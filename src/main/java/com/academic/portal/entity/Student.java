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
@Table(name = "STUDENTS",
        uniqueConstraints = {
                @UniqueConstraint(name = "UQ_STUDENTS_USER", columnNames = {"USER_ID"})
        })
@ToString(exclude = {"user", "address"})
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_seq")
    @SequenceGenerator(
            name = "student_seq",
            sequenceName = "SEQ_STUDENTS",
            allocationSize = 1
    )
    @Column(name = "STUDENT_ID")
    private Integer studentId;

    @Column(name = "NATIONAL_ID", length = 14)
    private String nationalId;

    @Column(name = "PASSPORT_NUMBER", length = 20)
    private String passportNumber;

    @Column(name = "IDENTITY_TYPE", nullable = false)
    private IdentityType identityType;

    @Column(name = "IS_ACTIVE", nullable = false)
    private ActiveStatus isActive;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false, unique = true,
            foreignKey = @ForeignKey(name = "FK_STUDENTS_USER"))
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ADDRESS_ID",
            foreignKey = @ForeignKey(name = "FK_STUDENTS_ADDRESS"))
    private Address address;

    @PrePersist
    private void prePersist() {
        if (isActive == null) isActive = ActiveStatus.ACTIVE;
    }
}
