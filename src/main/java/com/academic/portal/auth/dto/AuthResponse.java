package com.academic.portal.auth.dto;

import com.academic.portal.enums.Role;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AuthResponse {
    private Integer userId;
    private String accessToken;
    private String refreshToken;
    private String username;
    private String firstName;
    private String lastName;
    private String mobileNumber;
    private String email;
    private Role role;


  }
