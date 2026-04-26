package com.academic.portal.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Getter
@Setter
@Table(name = "REFRESH_TOKENS")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "refresh_seq")
    @SequenceGenerator(
            name = "refresh_seq",
            sequenceName = "SEQ_REFRESH_TOKENS",
            allocationSize = 1
    )
    @Column(name = "ID")
    private Long id;

    @Column(name = "TOKEN", nullable = false, unique = true, length = 500)
    private String token;

    @Column(name = "EXPIRY_DATE", nullable = false)
    private Instant expiryDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false,
            foreignKey = @ForeignKey(name = "FK_REFRESH_TOKENS_USER"))
    private User user;
}