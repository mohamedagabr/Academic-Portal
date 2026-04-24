package com.academic.portal.security;

import com.academic.portal.common.response.ApiResponse;
import com.academic.portal.common.exception.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;
    private final CacheManager cacheManager;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        // if no token, continue
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String token = authHeader.substring(7);
            String username = jwtService.extractUsername(token);

            if (username != null &&
                    SecurityContextHolder.getContext().getAuthentication() == null) {

                UserDetails userDetails =
                        customUserDetailsService.loadUserByUsername(username);

                if (jwtService.isTokenValid(token, userDetails)) {

                    var cache = cacheManager.getCache("sessions");

                    if (cache == null) {
                        unauthorized(response, ErrorCode.SESSION_EXPIRED.name());
                        return;
                    }

                    var session = cache.get(username);

                    System.out.println("SESSION CHECK FOR: " + username);
                    System.out.println("CACHE VALUE: " + (session != null ? session.get() : null));

                    if (session == null) {
                        unauthorized(response, ErrorCode.SESSION_EXPIRED.name());
                        return;
                    }

                    // refresh idle session
                    cache.put(username, System.currentTimeMillis());

                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }

            filterChain.doFilter(request, response);

        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            unauthorized(response, ErrorCode.TOKEN_EXPIRED.name());
        } catch (io.jsonwebtoken.JwtException e) {
            unauthorized(response, ErrorCode.INVALID_TOKEN.name());
        } catch (Exception e) {
            unauthorized(response, ErrorCode.INVALID_TOKEN.name());
        }
    }

//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//
//        String authHeader = request.getHeader("Authorization");
//        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        try {
//            String token = authHeader.substring(7);
//            String username = jwtService.extractUsername(token);
//            if (username !=null && SecurityContextHolder.getContext().getAuthentication() == null) {
//                UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
//                if (jwtService.isTokenValid(token, userDetails)) {
//                    UsernamePasswordAuthenticationToken authToken =
//                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//                    SecurityContextHolder.getContext().setAuthentication(authToken);
//                }
//            }
//            filterChain.doFilter(request, response);
//
//        } catch (io.jsonwebtoken.ExpiredJwtException e) {
//            unauthorized(response, ErrorCode.TOKEN_EXPIRED.name());
//            return;
//        } catch (io.jsonwebtoken.JwtException e) {
//            unauthorized(response, ErrorCode.INVALID_TOKEN.name());
//            return;
//        } catch (Exception e) {
//            unauthorized(response, ErrorCode.INVALID_TOKEN.name());
//            return;
//        }
//
//    }

    private void unauthorized(HttpServletResponse response, String message) throws IOException {
        if (response.isCommitted()) return;

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        ApiResponse<Void> body = ApiResponse.error(message);

        new ObjectMapper().writeValue(response.getWriter(), body);
    }
}
