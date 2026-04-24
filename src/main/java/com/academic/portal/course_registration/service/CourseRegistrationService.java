package com.academic.portal.course_registration.service;


import com.academic.portal.common.exception.BusinessException;
import com.academic.portal.common.exception.ErrorCode;
import com.academic.portal.course.repository.CourseRepository;
import com.academic.portal.course_registration.dto.CourseRegistrationRequestDto;
import com.academic.portal.course_registration.dto.CourseRegistrationResponseDto;
import com.academic.portal.course_registration.mapper.CourseRegistrationMapper;
import com.academic.portal.course_registration.repository.CourseRegistrationRepository;
import com.academic.portal.entity.Course;
import com.academic.portal.entity.CourseRegistration;
import com.academic.portal.entity.Student;
import com.academic.portal.enums.ActiveStatus;
import com.academic.portal.enums.CourseRegistrationStatus;
import com.academic.portal.student.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseRegistrationService {

    private final CourseRegistrationRepository courseRegistrationRepository;
    private final CourseRegistrationMapper courseRegistrationMapper;

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;


    @Transactional
    @CacheEvict(value = "course_schedule", allEntries = true)
    public CourseRegistrationResponseDto register(CourseRegistrationRequestDto dto) {

        // check student exists
        Student student = studentRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new BusinessException(ErrorCode.STUDENT_NOT_FOUND));

        // check student active
        if (student.getIsActive() != ActiveStatus.ACTIVE) {
            throw new BusinessException(ErrorCode.USER_NOT_ACTIVE);
        }

        // check course exists
        Course course = courseRepository.findById(dto.getCourseId())
                .orElseThrow(() -> new BusinessException(ErrorCode.COURSE_NOT_FOUND));

        Optional<CourseRegistration> existing = courseRegistrationRepository
                .findByStudent_StudentIdAndCourse_CourseId(
                        dto.getStudentId(),
                        dto.getCourseId()
                );


        if (existing.isPresent()) {

            CourseRegistration reg = existing.get();

            if (reg.getRegistrationStatus() == CourseRegistrationStatus.REGISTERED) {
                throw new BusinessException(ErrorCode.COURSE_ALREADY_REGISTERED);
            }

            reg.setRegistrationStatus(CourseRegistrationStatus.REGISTERED);
            return courseRegistrationMapper.toDto(reg);
        }

        // check capacity
        long registeredCount = courseRegistrationRepository
                .countByCourse_CourseIdAndRegistrationStatus(
                        dto.getCourseId(),
                        CourseRegistrationStatus.REGISTERED
                );

        if (registeredCount >= course.getCapacity()) {
            throw new BusinessException(ErrorCode.COURSE_FULL);
        }

        //  map + save
        CourseRegistration entity = courseRegistrationMapper.toEntity(dto);

        // Linking full entities not just IDs
        entity.setStudent(student);
        entity.setCourse(course);

        CourseRegistration saved = courseRegistrationRepository.save(entity);

        // return response
        return courseRegistrationMapper.toDto(saved);
    }



    @Transactional
    @CacheEvict(value = "course_schedule", allEntries = true)
    public CourseRegistrationResponseDto cancel(CourseRegistrationRequestDto dto) {

        CourseRegistration registration = courseRegistrationRepository
                .findByStudent_StudentIdAndCourse_CourseId(
                        dto.getStudentId(),
                        dto.getCourseId()
                )
                .orElseThrow(() -> new BusinessException(ErrorCode.REGISTRATION_NOT_FOUND));

        if (registration.getRegistrationStatus() == CourseRegistrationStatus.CANCELED) {
            throw new BusinessException(ErrorCode.ALREADY_CANCELED);
        }

        registration.setRegistrationStatus(CourseRegistrationStatus.CANCELED);

        CourseRegistration updated = courseRegistrationRepository.save(registration);

        return courseRegistrationMapper.toDto(updated);
    }

}