package com.academic.portal.course_registration.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseRegistrationRequestDto {

    @NotNull
    private Integer studentId;
    @NotNull
    private Integer courseId;
}
