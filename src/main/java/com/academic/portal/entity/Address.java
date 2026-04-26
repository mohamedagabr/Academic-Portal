package com.academic.portal.entity;
import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name = "ADDRESS")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_seq")
    @SequenceGenerator(
            name = "address_seq",
            sequenceName = "SEQ_ADDRESS",
            allocationSize = 1
    )
    @Column(name = "ADDRESS_ID")
    private Integer addressId;

    @Column(name = "ADDRESS_NAME", nullable = false, length = 255)
    private String addressName;
}
