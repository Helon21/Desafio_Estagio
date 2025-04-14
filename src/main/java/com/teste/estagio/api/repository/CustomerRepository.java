package com.teste.estagio.api.repository;

import com.teste.estagio.api.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    boolean existsByEmail(String email);
    boolean existsByCpf(String cpf);

    @Query("SELECT DISTINCT c FROM Customer c LEFT JOIN FETCH c.addresses WHERE c.id = :id")
    Optional<Customer> findByIdWithAddresses(@Param("id") UUID id);

    @Query("SELECT DISTINCT c FROM Customer c LEFT JOIN FETCH c.addresses")
    List<Customer> findAllWithAddresses();
}