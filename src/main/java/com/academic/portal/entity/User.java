package com.academic.portal.entity;

import com.academic.portal.enums.*;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "USERS")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
@ToString(exclude = "refreshTokens")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(
            name = "user_seq",
            sequenceName = "SEQ_USERS",
            allocationSize = 1
    )
    @Column(name = "USER_ID")
    private Integer userId;

    @Column(name = "USER_NAME", nullable = false, length = 30, unique = true)
    private String username;

    @Column(name = "PASSWORD", nullable = false, length = 255)
    private String password;

    @Column(name = "FIRST_NAME", nullable = false, length = 30)
    private String firstName;

    @Column(name = "LAST_NAME", nullable = false, length = 30)
    private String lastName;

    @Column(name = "MOBILE_NUMBER", nullable = false, length = 11, unique = true)
    private String mobileNumber;

    @Column(name = "EMAIL", nullable = false, length = 50, unique = true)
    private String email;

    @Column(name = "GENDER", nullable = false)
    private Gender gender;

    @Column(name = "ROLE", nullable = false)
    private Role role;

    @Column(name = "IS_ACTIVE", nullable = false)
    private ActiveStatus isActive;

    @Column(name = "IS_DELETED", nullable = false)
    private DeletedFlag isDeleted;

    @Column(name = "CREATED_AT", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
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
