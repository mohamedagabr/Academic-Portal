package com.academic.portal.course_registration.dto;

import com.academic.portal.enums.CourseRegistrationStatus;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseRegistrationResponseDto {

    private Integer id;
    private Integer studentId;
    private Integer courseId;
    private CourseRegistrationStatus status;

}
