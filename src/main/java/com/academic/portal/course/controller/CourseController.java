package com.academic.portal.course.controller;

import com.academic.portal.common.response.ApiResponse;
import com.academic.portal.course.dto.CourseDepartmentResponseDto;
import com.academic.portal.course.dto.CourseResponseDto;
import com.academic.portal.course.service.CourseService;
import com.academic.portal.enums.ResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<CourseDepartmentResponseDto>>> getAllCourses(){
        List<CourseDepartmentResponseDto> courses = courseService.getAllCourses();
        return ResponseEntity.ok(
                ApiResponse.success(ResponseCode.COURSES_FETCHED, courses)
        );
    }
}
