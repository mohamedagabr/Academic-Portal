package com.academic.portal.student.controller;

import com.academic.portal.common.response.ApiResponse;
import com.academic.portal.enums.ResponseCode;
import com.academic.portal.student.dto.StudentRequestDto;
import com.academic.portal.student.dto.StudentResponseDto;
import com.academic.portal.student.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/students")

public class StudentController {

    private final StudentService studentService;

    @PostMapping
    public ResponseEntity<ApiResponse<StudentResponseDto>> addStudent(
            @Valid @RequestBody StudentRequestDto dto) {

        StudentResponseDto student = studentService.addStudent(dto);

        return ResponseEntity.ok(
                ApiResponse.success(ResponseCode.STUDENT_ADDED, student)
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<StudentResponseDto>>> getAllStudents() {

        List<StudentResponseDto> students = studentService.getAllStudents();

        return ResponseEntity.ok(
                ApiResponse.success(ResponseCode.STUDENTS_FETCHED, students)
        );
    }
}