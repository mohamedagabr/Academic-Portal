package com.academic.portal.course.dto;

import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class CourseResponseDto {

     private Integer courseId;
     private String courseName;
     private String courseCode;
     private Integer capacity;

}
