package com.academic.portal.student.dto;

import com.academic.portal.enums.ActiveStatus;
import com.academic.portal.enums.IdentityType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class StudentRequestDto {

    @NotNull(message = "UserId is required")
    private Integer userId ;
    @Pattern(regexp = "^[0-9]{14}$", message = "National ID must be 14 digits")
    private String nationalId;
    private String passportNumber;
    @NotNull(message = "IdentityType is required")
    private IdentityType identityType;
    private String addressName;

}
