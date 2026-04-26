package com.academic.portal.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "COURSE_DEPARTMENTS",
        uniqueConstraints = {
                @UniqueConstraint(name = "UQ_COURSE_DEPARTMENTS_NAME",
                        columnNames = {"COURSE_DEPARTMENT_NAME"})
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseDepartment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "course_dept_seq")
    @SequenceGenerator(
            name = "course_dept_seq",
            sequenceName = "SEQ_COURSE_DEPARTMENTS",
            allocationSize = 1
    )
    @Column(name = "COURSE_DEPARTMENT_ID")
    private Integer courseDepartmentId;

    @Column(name = "COURSE_DEPARTMENT_NAME", nullable = false, length = 50)
    private String courseDepartmentName;
}