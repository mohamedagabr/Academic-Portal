package com.academic.portal.entity;
import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name = "course_departments", schema = "dbo",
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_course_departments_name", columnNames = {"course_department_name"})
        })

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class CourseDepartment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_department_id")
    private Integer courseDepartmentId;

    @Column(name = "course_department_name", nullable = false, length = 50)
    private String courseDepartmentName;
}
