package com.academic.portal.course.mapper;

import com.academic.portal.course.dto.CourseResponseDto;
import com.academic.portal.entity.Course;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    CourseResponseDto toCourseDto(Course course);
}
