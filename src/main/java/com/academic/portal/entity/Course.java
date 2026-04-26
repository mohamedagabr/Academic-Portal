package com.academic.portal.entity;

import com.academic.portal.enums.ActiveStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "COURSES", schema = "ACADEMIC_PORTAL",
        uniqueConstraints = {
                @UniqueConstraint(name = "UQ_COURSES_NAME", columnNames = {"COURSE_NAME"}),
                @UniqueConstraint(name = "UQ_COURSES_CODE", columnNames = {"COURSE_CODE"})
        })
@ToString(exclude = {"registrations", "createdBy", "lastUpdatedBy"})
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "course_seq")
    @SequenceGenerator(
            name = "course_seq",
            sequenceName = "SEQ_COURSES",
            allocationSize = 1
    )
    @Column(name = "COURSE_ID")
    private Integer courseId;

    @Column(name = "COURSE_NAME", nullable = false, length = 50)
    private String courseName;

    @Column(name = "COURSE_CODE", length = 10)
    private String courseCode;

    @Column(name = "CAPACITY", nullable = false)
    private Integer capacity;

    @Column(name = "IS_ACTIVE", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private ActiveStatus isActive;

    @CreationTimestamp
    @Column(name = "CREATED_AT", nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "LAST_UPDATED_AT")
    private LocalDateTime lastUpdatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COURSE_DEPARTMENT_ID", nullable = false,
            foreignKey = @ForeignKey(name = "FK_COURSES_DEPARTMENT"))

    private CourseDepartment courseDepartment;

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    private Set<CourseRegistration> registrations;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CREATED_BY", nullable = false, updatable = false,
            foreignKey = @ForeignKey(name = "FK_COURSES_CREATED_BY"))
    private User createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LAST_UPDATED_BY",
            foreignKey = @ForeignKey(name = "FK_COURSES_UPDATED_BY"))
    private User lastUpdatedBy;

    @PrePersist
    private void prePersist() {
        if (isActive == null) isActive = ActiveStatus.ACTIVE;
    }
}