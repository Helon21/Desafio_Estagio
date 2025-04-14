package com.teste.estagio.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="customer")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name")
    private String name;
    @Column(name = "lastname")
    private String lastName;
    @Column(name = "email", unique = true)
    private String email;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "phone")
    private String phone;
    @Column(name = "birthdate")
    private LocalDate birthDate;
    @Column(name = "cpf", unique = true)
    private String cpf;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Address> addresses = new ArrayList<>();
}
