package com.academic.portal.course.dto;

import lombok.*;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseDepartmentResponseDto {
    private String courseDepartmentName;
    private List<CourseResponseDto> courses;
}
