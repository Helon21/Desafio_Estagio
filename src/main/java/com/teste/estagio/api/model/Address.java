package com.teste.estagio.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name="address")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String street;
    private String city;
    private String state;
    @Column(name = "postal_code")
    private String postalCode;
    private String country;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
