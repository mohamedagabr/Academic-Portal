package com.academic.portal.course.service;

import com.academic.portal.course.dto.CourseDepartmentResponseDto;
import com.academic.portal.course.dto.CourseResponseDto;
import com.academic.portal.course.mapper.CourseMapper;
import com.academic.portal.course.repository.CourseRepository;
import com.academic.portal.entity.Course;
import com.academic.portal.entity.CourseDepartment;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    @Cacheable("courses")
    public List<CourseDepartmentResponseDto> getAllCourses() {

        System.out.println("DB Call - getAllCourses() EXECUTED");

        List<Course> courses = courseRepository.findAllWithDepartment();
        return courses.stream()
                .collect(Collectors.groupingBy(
                        c -> c.getCourseDepartment() != null
                                ? c.getCourseDepartment().getCourseDepartmentName()
                                : "Department"
                ))
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> CourseDepartmentResponseDto.builder()
                        .courseDepartmentName(entry.getKey())
                        .courses(
                                entry.getValue().stream()
                                        .sorted(Comparator.comparing(Course::getCourseName))
                                        .map(courseMapper::toCourseDto)
                                        .toList()
                        )
                        .build()
                )
                .toList();
    }



}
