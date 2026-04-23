package com.academic.portal.auth.service;

import com.academic.portal.auth.dto.AuthRequest;
import com.academic.portal.auth.dto.AuthResponse;
import com.academic.portal.auth.dto.LoginDto;
import com.academic.portal.entity.User;
import com.academic.portal.entity.RefreshToken;
import com.academic.portal.common.exception.BusinessException;
import com.academic.portal.common.exception.ErrorCode;
import com.academic.portal.enums.ActiveStatus;
import com.academic.portal.enums.DeletedFlag;
import com.academic.portal.user.repository.UserRepository;
import com.academic.portal.security.JwtService;
import com.academic.portal.token.service.RefreshTokenService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

//    public AuthResponse register(AuthRequest request) {
//
//        userValidation(request);
//        String rawPassword = request.getPassword(); // save password before hashing
//
//        User user = User.builder()
//                .username(request.getUsername())
//                .password(passwordEncoder.encode(request.getPassword()))
//                .email(request.getEmail())
//                .build();
//
//        userRepository.save(user);
//
//        String accessToken = jwtService.generateAccessToken(user);
//        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);
//
//        return buildAuthResponse(user, accessToken, refreshToken.getToken());
//    }

    public AuthResponse login(LoginDto dto) {

        User user = userRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        if (user.getIsActive().equals(ActiveStatus.INACTIVE)){
            throw new BusinessException(ErrorCode.USER_NOT_ACTIVE);
        }

        if (user.getIsDeleted().equals(DeletedFlag.YES)){
            throw new BusinessException(ErrorCode.USER_IS_DELETED);
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.getUsername(),
                        dto.getPassword()
                )
        );

        String accessToken = jwtService.generateAccessToken(user);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);

        return buildAuthResponse(user, accessToken, refreshToken.getToken());
    }

    public AuthResponse refresh(String refreshToken) {

        if (refreshToken == null || refreshToken.isBlank()) {
            throw new BusinessException(ErrorCode.REFRESH_TOKEN_REQUIRED);
        }

        refreshTokenService.validateRefreshToken(refreshToken);

        String username = jwtService.extractUsername(refreshToken);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        String newAccessToken = jwtService.generateAccessToken(user);

        return buildAuthResponse(user, newAccessToken, refreshToken);
    }


    public void logout(HttpServletRequest request) {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new BusinessException(ErrorCode.INVALID_TOKEN);
        }

        String token = authHeader.substring(7);
        String username = jwtService.extractUsername(token);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        refreshTokenService.deleteByUser(user);
    }

    private void userValidation(AuthRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new BusinessException(ErrorCode.USERNAME_ALREADY_EXISTS);
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }

        if (userRepository.existsByMobileNumber(request.getMobileNumber())) {
            throw new BusinessException(ErrorCode.MOBILE_NUMBER_ALREADY_EXISTS);
        }
    }

    private AuthResponse buildAuthResponse(User user, String accessToken, String refreshToken) {

        return AuthResponse.builder()
                .userId(user.getUserId())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
}
