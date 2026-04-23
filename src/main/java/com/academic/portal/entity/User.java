package com.academic.portal.entity;

import com.academic.portal.enums.*;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "users", schema = "dbo",
        uniqueConstraints = {
                @UniqueConstraint(name = "uq_users_username", columnNames = {"user_name"}),
                @UniqueConstraint(name = "uq_users_mobile", columnNames = {"mobile_number"}),
                @UniqueConstraint(name = "uq_users_email", columnNames = {"email"})
        })
@Builder
@ToString(exclude = "refreshTokens")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "user_name", nullable = false, length = 30)
    private String username;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "first_name", nullable = false, length = 30)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 30)
    private String lastName;

    @Column(name = "mobile_number", nullable = false, length = 11)
    private String mobileNumber;

    @Column(nullable = false, length = 50)
    private String email;


    @Column(name = "gender", nullable = false)
    private Gender gender;


    @Column(name = "role", nullable = false)
    private Role role;


    @Column(name = "is_active", nullable = false)
    private ActiveStatus isActive;


    @Column(name = "is_deleted", nullable = false)
    private DeletedFlag isDeleted;


    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt ;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt ;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    @Builder.Default
    private List<RefreshToken> refreshTokens = new ArrayList<>();

    @PrePersist
    private void prePersist() {
        if (isActive == null) isActive = ActiveStatus.ACTIVE;
        if (isDeleted == null) isDeleted = DeletedFlag.NO;
        if (role == null) role = Role.USER;
        if (createdAt == null) createdAt = LocalDateTime.now();
        if (updatedAt == null) updatedAt = createdAt;
    }

    @PreUpdate
    private void preUpdate() {
        updatedAt = LocalDateTime.now();
    }

}
