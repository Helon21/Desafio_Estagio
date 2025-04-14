package com.teste.estagio.api.model.dto.customer;

import com.teste.estagio.api.model.dto.address.AddressDTO;

import java.time.LocalDate;
import java.util.List;

public record CustomerUpdateRequestDTO(
        String name,
        String lastName,
        String email,
        String password,
        String phone,
        LocalDate birthDate,
        List<AddressDTO> addresses
) {
}
