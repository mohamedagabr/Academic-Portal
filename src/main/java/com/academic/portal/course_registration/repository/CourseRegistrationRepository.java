package com.academic.portal.course_registration.repository;

import com.academic.portal.course_registration.dto.CourseScheduleReportDto;
import com.academic.portal.entity.CourseRegistration;
import com.academic.portal.enums.CourseRegistrationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CourseRegistrationRepository extends JpaRepository<CourseRegistration, Integer> {

    boolean existsByStudent_StudentIdAndCourse_CourseId(
            Integer studentId,
            Integer courseId);

    long countByCourse_CourseIdAndRegistrationStatus(
            Integer courseId,
            CourseRegistrationStatus status);

    Optional<CourseRegistration> findByStudent_StudentIdAndCourse_CourseId(
            Integer studentId,
            Integer courseId
    );

    @Modifying
    @Query("""
    update CourseRegistration r
    set r.registrationStatus = :status,
        r.lastUpdatedAt = CURRENT_TIMESTAMP
    where r.student.studentId = :studentId
    and r.course.courseId = :courseId
    """)
    int cancelRegistration(
            @Param("studentId") Integer studentId,
            @Param("courseId") Integer courseId,
            @Param("status") CourseRegistrationStatus status
    );


}
