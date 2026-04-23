package com.academic.portal.course_registration.mapper;

import com.academic.portal.course_registration.dto.CourseRegistrationRequestDto;
import com.academic.portal.course_registration.dto.CourseRegistrationResponseDto;
import com.academic.portal.entity.Course;
import com.academic.portal.entity.CourseRegistration;
import com.academic.portal.entity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CourseRegistrationMapper {

    @Mapping(target = "studentId", source = "student.studentId")
    @Mapping(target = "courseId", source = "course.courseId")
    @Mapping(target = "status", source = "registrationStatus")
    CourseRegistrationResponseDto toDto(CourseRegistration entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "student", source = "studentId")
    @Mapping(target = "course", source = "courseId")
    CourseRegistration toEntity(CourseRegistrationRequestDto dto);

    // helper methods
    default Student mapStudent(Integer studentId) {
        if (studentId == null) return null;
        Student student = new Student();
        student.setStudentId(studentId);
        return student;
    }

    default Course mapCourse(Integer courseId) {
        if (courseId == null) return null;
        Course course = new Course();
        course.setCourseId(courseId);
        return course;
    }
}
