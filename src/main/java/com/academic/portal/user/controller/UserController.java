package com.academic.portal.user.controller;

import com.academic.portal.common.response.ApiResponse;
import com.academic.portal.auth.dto.AuthResponse;
import com.academic.portal.user.dto.UserRequestDto;
import com.academic.portal.user.dto.UserResponseDto;
import com.academic.portal.enums.ResponseCode;
import com.academic.portal.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<List<UserResponseDto>>> getAllUsers() {

        return ResponseEntity.ok(
                ApiResponse.success(
                        ResponseCode.USERS_FETCHED,
                        userService.getAllUsers()
                )
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<UserResponseDto>> getUserById(@PathVariable Integer id) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        ResponseCode.USER_FETCHED,
                        userService.getUserById(id)
                )
        );
    }

    @PostMapping
   @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<AuthResponse>> createUser(
            @Valid @RequestBody UserRequestDto request
    ) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        ResponseCode.USER_CREATED,
                        userService.createUser(request)
                )
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<UserResponseDto>> updateUser(
            @PathVariable Integer id,
            @RequestBody UserRequestDto request
    ) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        ResponseCode.USER_UPDATED,
                        userService.updateUser(id, request)
                )
        );
    }



    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteUser(
            @PathVariable Integer id) {

        userService.softDelete(id);

        return ResponseEntity.ok(
                ApiResponse.success(ResponseCode.USER_DELETED, null)
        );
    }

}
