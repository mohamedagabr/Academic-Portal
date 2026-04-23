package com.academic.portal.user.dto;

import com.academic.portal.enums.ActiveStatus;
import com.academic.portal.enums.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDto {

    private Integer userId;
    private String username;
    private String firstName;
    private String lastName;
    private String mobileNumber;
    private String email;
    private Role role;
    private ActiveStatus isActive;
}
