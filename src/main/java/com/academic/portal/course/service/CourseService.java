package com.academic.portal.course.service;

import com.academic.portal.course.dto.CourseDepartmentResponseDto;
import com.academic.portal.course.dto.CourseResponseDto;
import com.academic.portal.course.mapper.CourseMapper;
import com.academic.portal.course.repository.CourseRepository;
import com.academic.portal.entity.Course;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    @Cacheable("courses")
    public List<CourseDepartmentResponseDto> getAllCourses() {

        List<Course> courses = courseRepository.findAll();

        return courses.stream()
                .collect(Collectors.groupingBy(
                        course -> course.getCourseDepartment().getCourseDepartmentName()
                ))
                .entrySet()
                .stream()
                .map(entry -> CourseDepartmentResponseDto.builder()
                        .courseDepartmentName(entry.getKey())
                        .courses(
                                entry.getValue().stream()
                                        .map(courseMapper::toCourseDto)
                                        .toList()
                        )
                        .build()
                )
                .toList();
    }
}
