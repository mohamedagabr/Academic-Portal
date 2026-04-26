package com.academic.portal.course.repository;

import com.academic.portal.entity.Course;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
public interface CourseRepository extends JpaRepository<Course, Integer>{



    @Query("""
        SELECT c FROM Course c
                 JOIN FETCH c.courseDepartment
        """)
    List<Course> findAllWithDepartment();


    @Query("""
     SELECT
        c.courseId,
        c.courseName,
        c.courseCode,
        c.capacity,
        c.courseDepartment.courseDepartmentName,
        SUM(CASE WHEN r.registrationStatus = 1 THEN 1 ELSE 0 END)
    FROM Course c
    LEFT JOIN CourseRegistration r
        ON r.course.courseId = c.courseId
    WHERE (:departmentId IS NULL OR c.courseDepartment.courseDepartmentId = :departmentId)
    GROUP BY
        c.courseId,
        c.courseName,
        c.courseCode,
        c.capacity,
        c.courseDepartment.courseDepartmentName
    """)
    List<Object[]> getCourseScheduleData(@Param("departmentId") Integer departmentId);



    @EntityGraph(attributePaths = {
            "courseDepartment",
            "registrations"
    })
    List<Course> findByCourseDepartment_CourseDepartmentId(Integer departmentId);


}
