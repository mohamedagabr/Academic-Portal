package com.academic.portal.entity;
import com.academic.portal.enums.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Set;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
@Entity
@Table(name = "courses", schema = "dbo",
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_courses_name", columnNames = {"course_name"}),
                @UniqueConstraint(name = "uq_courses_code", columnNames = {"course_code"})
        })
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Integer courseId;

    @Column(name = "course_name", nullable = false, length = 50)
    private String courseName;

    @Column(name = "course_code", nullable = true, length = 10)
    private String courseCode;

    @Min(1)
    @Column(name = "capacity" , nullable = false)
    private Integer capacity;

    @Column(name = "is_active", nullable = false)
    private ActiveStatus isActive ;

    @Column(name = "created_at",nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt ;

    @Column(name = "last_updated_at" ,nullable = true)
    @CreationTimestamp
    private LocalDateTime lastUpdatedAt ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_department_id", nullable = false,
    foreignKey = @ForeignKey(name = "fk_courses_department"))
    private CourseDepartment courseDepartment;


    @OneToMany(mappedBy = "course",fetch = FetchType.LAZY)
    private Set<CourseRegistration> registrations;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false, updatable = false,
    foreignKey = @ForeignKey(name = "fk_courses_created_by"))
   private User createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "last_updated_by", nullable = true,
            foreignKey = @ForeignKey(name = "fk_courses_updated_by"))
   private User lastUpdatedBy;


    @PrePersist
    private void prePersist() {
        if (isActive == null) isActive = ActiveStatus.ACTIVE;
    }






}
