package com.academic.portal.entity;

import com.academic.portal.enums.CourseRegistrationStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "COURSE_REGISTRATIONS",
        uniqueConstraints = {
                @UniqueConstraint(name = "UQ_STUDENT_COURSE",
                        columnNames = {"STUDENT_ID", "COURSE_ID"})
        },
        indexes = {
                @Index(name = "IDX_REG_STUDENT", columnList = "STUDENT_ID"),
                @Index(name = "IDX_REG_COURSE", columnList = "COURSE_ID")
        })
public class CourseRegistration {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "course_reg_seq")
    @SequenceGenerator(
            name = "course_reg_seq",
            sequenceName = "SEQ_COURSE_REGISTRATIONS",
            allocationSize = 1
    )
    @Column(name = "ID")
    private Integer id;

    @Column(name = "REGISTRATION_STATUS", nullable = false)
    private CourseRegistrationStatus registrationStatus;

    @CreationTimestamp
    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "LAST_UPDATED_AT")
    private LocalDateTime lastUpdatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STUDENT_ID", nullable = false,
            foreignKey = @ForeignKey(name = "FK_REGISTRATIONS_STUDENT"))
    @ToString.Exclude
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "COURSE_ID", nullable = false,
            foreignKey = @ForeignKey(name = "FK_REGISTRATIONS_COURSE"))
    @ToString.Exclude
    private Course course;

    @PrePersist
    private void prePersist() {
        if (registrationStatus == null)
            registrationStatus = CourseRegistrationStatus.REGISTERED;
    }
}