package com.academic.portal.user.dto;

import com.academic.portal.enums.ActiveStatus;
import com.academic.portal.enums.DeletedFlag;
import com.academic.portal.enums.Gender;
import com.academic.portal.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequestDto {

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
    @NotNull
    private Gender gender;

    private Role role;
    private ActiveStatus isActive;
    private DeletedFlag isDeleted;



}
