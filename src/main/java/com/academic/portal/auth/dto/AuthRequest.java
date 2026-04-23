package com.academic.portal.auth.dto;

import com.academic.portal.enums.Gender;
import com.academic.portal.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class AuthRequest {
    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String mobileNumber;

    @NotBlank
    private String email;

    @NotBlank
    private Gender gender;

    @NotBlank
    private Role role;
}

