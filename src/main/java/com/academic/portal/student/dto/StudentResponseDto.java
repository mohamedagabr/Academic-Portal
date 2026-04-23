package com.academic.portal.student.dto;
import com.academic.portal.enums.IdentityType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentResponseDto {

    private Integer studentId;
    private Integer userId;
    private String firstName;
    private String lastName;
    private String nationalId;
    private String passportNumber;
    private IdentityType identityType;
    private String addressName;

}
