package com.academic.portal.user.dto;

import com.academic.portal.enums.ActiveStatus;
import com.academic.portal.enums.DeletedFlag;
import com.academic.portal.enums.Gender;
import com.academic.portal.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password must contain at least 8 characters, one uppercase letter," +
                    " one lowercase letter, one number and one special character.")
    private String password;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    @Pattern(regexp = "^01[0-2,5]{1}[0-9]{8}$", message = "Mobile Number Not Valid")
    private String mobileNumber;

    @NotBlank
    @Email(message = "Email Not Valid")
    private String email;
    @NotNull
    private Gender gender;

    private Role role;
    private ActiveStatus isActive;
    private DeletedFlag isDeleted;



}
