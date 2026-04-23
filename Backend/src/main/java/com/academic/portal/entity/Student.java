package com.academic.portal.entity;
import com.academic.portal.enums.ActiveStatus;
import com.academic.portal.enums.IdentityType;
import com.academic.portal.enums.Role;
import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
@Entity
@Table(name = "students", schema = "dbo",
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_students_user", columnNames = {"user_id"})
        })
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Integer studentId;

    @Column(name = "national_id", length = 14)
    private String nationalId;

    @Column(name = "passport_number", length = 20)
    private String passportNumber;

    @Column(name = "identity_type",nullable = false)
    private IdentityType identityType;

    @Column(name = "is_active", nullable = false)
    private ActiveStatus isActive;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private Address address;


    @PrePersist
    private void prePersist() {
        if (isActive == null) isActive = ActiveStatus.ACTIVE;
    }



}
