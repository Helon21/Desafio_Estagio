package com.teste.estagio.api.repository;

import com.teste.estagio.api.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, UUID> {
}
