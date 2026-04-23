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

@Service
@RequiredArgsConstructor
public class CourseRegistrationService {

    private final CourseRegistrationRepository courseRegistrationRepository;
    private final CourseRegistrationMapper courseRegistrationMapper;

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

//    @CacheEvict(value = "course_schedule", allEntries = true)
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

        // check already registered
        boolean exists = courseRegistrationRepository
                .existsByStudent_StudentIdAndCourse_CourseId(
                        dto.getStudentId(),
                        dto.getCourseId()
                );

        if (exists) {
            throw new BusinessException(ErrorCode.COURSE_ALREADY_REGISTERED);
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
//    @CacheEvict(value = "course_schedule", allEntries = true)
    public CourseRegistrationResponseDto cancel(CourseRegistrationRequestDto dto) {

        // execute update
        int updatedRows = courseRegistrationRepository.cancelRegistration(
                dto.getStudentId(),
                dto.getCourseId(),
                CourseRegistrationStatus.CANCELED
        );

        if (updatedRows == 0) {
            throw new BusinessException(ErrorCode.REGISTRATION_NOT_FOUND);
        }


        // get registration after update
        CourseRegistration registration = courseRegistrationRepository
                .findByStudent_StudentIdAndCourse_CourseId(
                        dto.getStudentId(),
                        dto.getCourseId()
                )
                .orElseThrow(() -> new BusinessException(ErrorCode.REGISTRATION_NOT_FOUND));

        return courseRegistrationMapper.toDto(registration);
    }


//    public CourseRegistrationResponseDto cancel(CourseRegistrationRequestDto dto) {
//
//        // 1. get registration
//        CourseRegistration registration = courseRegistrationRepository
//                .findByStudent_StudentIdAndCourse_CourseId(
//                        dto.getStudentId(),
//                        dto.getCourseId()
//                )
//                .orElseThrow(() -> new BusinessException(ErrorCode.REGISTRATION_NOT_FOUND));
//
//        // 2. check already canceled
//        if (registration.getRegistrationStatus() == CourseRegistrationStatus.CANCELED) {
//            throw new BusinessException(ErrorCode.ALREADY_CANCELED);
//        }
//
//        // 3. update status
//        registration.setRegistrationStatus(CourseRegistrationStatus.CANCELED);
//
//        // (اختياري) update timestamp لو مش auto
//        // registration.setLastUpdatedAt(LocalDateTime.now());
//
//        CourseRegistration updated = courseRegistrationRepository.save(registration);
//
//        return courseRegistrationMapper.toDto(updated);
//    }

}