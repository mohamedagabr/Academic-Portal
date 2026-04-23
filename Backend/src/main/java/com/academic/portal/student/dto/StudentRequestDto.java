package com.academic.portal.student.dto;

import com.academic.portal.enums.ActiveStatus;
import com.academic.portal.enums.IdentityType;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class StudentRequestDto {

    @NotNull(message = "UserId is required")
    private Integer userId ;
    private String nationalId;
    private String passportNumber;
    @NotNull(message = "IdentityType is required")
    private IdentityType identityType;
    private String addressName;

}
