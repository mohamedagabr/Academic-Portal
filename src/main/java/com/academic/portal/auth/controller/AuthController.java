package com.academic.portal.auth.controller;

import com.academic.portal.common.response.ApiResponse;
import com.academic.portal.auth.dto.AuthRequest;
import com.academic.portal.auth.dto.AuthResponse;
import com.academic.portal.auth.dto.LoginDto;
import com.academic.portal.token.dto.RefreshTokenRequest;
import com.academic.portal.enums.ResponseCode;
import com.academic.portal.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Register user", security = @SecurityRequirement(name = "none"))
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@Valid @RequestBody AuthRequest request) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        ResponseCode.USER_REGISTERED,
                        authService.register(request)
                )
        );
    }

    @Operation(summary = "Login user", security = @SecurityRequirement(name = "none"))
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginDto loginDto) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        ResponseCode.LOGIN_SUCCESS,
                        authService.login(loginDto)
                )
        );
    }

//    @PostMapping("/refresh")
//    public ResponseEntity<ApiResponse<AuthResponse>> refresh(
//            @Valid @RequestBody RefreshTokenRequest request) {
//
//        return ResponseEntity.ok(
//                ApiResponse.success(
//                        ResponseCode.ACCESS_TOKEN_REFRESHED,
//                        authService.refresh(request.getRefreshToken())
//                )
//        );
//    }
//
//
//    @PostMapping("/logout")
//    public ResponseEntity<ApiResponse<String>> logout(HttpServletRequest request) {
//
//        authService.logout(request);
//
//        return ResponseEntity.ok(
//                ApiResponse.success(ResponseCode.LOGOUT_SUCCESS, null)
//        );
//    }



 }

