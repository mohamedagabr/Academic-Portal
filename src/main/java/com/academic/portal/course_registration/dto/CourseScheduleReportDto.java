package com.academic.portal.course_registration.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseScheduleReportDto {

    private Integer departmentId;
    private String departmentName;
    private String courseName;
    private String courseCode;
    private Integer capacity;
    private Long registeredCount;
    private Long availableSeats;

}
