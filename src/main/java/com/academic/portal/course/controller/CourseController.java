package com.academic.portal.course.controller;

import com.academic.portal.common.response.ApiResponse;
import com.academic.portal.course.dto.CourseDepartmentResponseDto;
import com.academic.portal.course.dto.CourseResponseDto;
import com.academic.portal.course.repository.CourseRepository;
import com.academic.portal.course.service.CourseService;
import com.academic.portal.entity.Course;
import com.academic.portal.enums.ResponseCode;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
