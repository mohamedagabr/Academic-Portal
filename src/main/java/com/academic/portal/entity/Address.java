package com.academic.portal.entity;
import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name = "address", schema = "dbo")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Integer addressId;

    @Column(name = "address_name", nullable = false, length = 255)
    private String addressName;
}
