package com.academic.portal.entity;
import com.academic.portal.enums.CourseRegistrationStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
@Entity
@Table(name = "course_registrations", schema = "dbo",
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_student_course",
                        columnNames = {"student_id", "course_id"})
        },
        indexes = {
                @Index(name = "idx_reg_student", columnList = "student_id"),
                @Index(name = "idx_reg_course", columnList = "course_id")
        })

public class CourseRegistration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "registration_status", nullable = false)
    private CourseRegistrationStatus registrationStatus;

    @Column(name = "created_at",nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt ;

    @Column(name = "last_updated_at" ,nullable = false)
    @UpdateTimestamp
    private LocalDateTime lastUpdatedAt ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_registrations_student"))
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_registrations_course"))
    private Course course;


    @PrePersist
    private void prePersist() {
        if(registrationStatus == null) registrationStatus = CourseRegistrationStatus.REGISTERED;
    }


}
