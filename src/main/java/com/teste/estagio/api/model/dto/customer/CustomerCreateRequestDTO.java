package com.teste.estagio.api.model.dto.customer;

import com.teste.estagio.api.model.Customer;
import com.teste.estagio.api.model.dto.address.AddressDTO;
import com.teste.estagio.api.utils.CPFFormatterUtils;
import com.teste.estagio.api.utils.ValidationMessages;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;
import java.util.List;

public record CustomerCreateRequestDTO(
        @NotBlank(message = "name" + ValidationMessages.NOT_BLANK)
        String name,
        @NotBlank(message = "last name" + ValidationMessages.NOT_BLANK)
        String lastName,
        @NotBlank(message = "email" + ValidationMessages.INVALID_EMAIL)
        @Email
        String email,
        @NotBlank(message = "password" + ValidationMessages.NOT_BLANK)
        String password,
        @NotBlank(message = "phone" + ValidationMessages.NOT_BLANK)
        String phone,
        @NotNull(message = "birthdate" + ValidationMessages.NOT_BLANK)
        LocalDate birthDate,
        @NotBlank(message = "CPF" + ValidationMessages.INVALID_CPF)
        @CPF
        String cpf,
        @NotEmpty(message = "Address" + ValidationMessages.NOT_EMPTY)
        List<AddressDTO> addresses
) {
        public CustomerCreateRequestDTO(Customer customer) {
                this(
                        customer.getName(),
                        customer.getLastName(),
                        customer.getEmail(),
                        customer.getPassword(),
                        customer.getPhone(),
                        customer.getBirthDate(),
                        CPFFormatterUtils.cleanCPF(customer.getCpf()),
                        customer.getAddresses().stream().map(address ->
                                new AddressDTO(
                                        address.getId(),
                                        address.getStreet(),
                                        address.getCity(),
                                        address.getState(),
                                        address.getPostalCode(),
                                        address.getCountry()
                                )).toList()
                        );
        }
}
