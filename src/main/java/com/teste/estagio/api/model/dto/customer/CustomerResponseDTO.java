package com.teste.estagio.api.model.dto.customer;

import com.teste.estagio.api.model.Customer;
import com.teste.estagio.api.model.dto.address.AddressDTO;
import com.teste.estagio.api.utils.CPFFormatterUtils;
import com.teste.estagio.api.utils.DateFormatterUtils;

import java.util.List;
import java.util.UUID;

public record CustomerResponseDTO(
        UUID id,
        String name,
        String lastName,
        String email,
        String phone,
        String cpf,
        String birthdate,
        List<AddressDTO> addresses

) {

    public CustomerResponseDTO(Customer customer) {
        this(
                customer.getId(),
                customer.getName(),
                customer.getLastName(),
                customer.getEmail(),
                customer.getPhone(),
                CPFFormatterUtils.formatCPF(customer.getCpf()),
                DateFormatterUtils.formatDate(customer.getBirthDate()),
                customer.getAddresses().stream()
                        .map(address -> new AddressDTO(
                                        address.getId(),
                                        address.getStreet(),
                                        address.getCity(),
                                        address.getState(),
                                        address.getPostalCode(),
                                        address.getCountry())).toList()
        );
    }
}
