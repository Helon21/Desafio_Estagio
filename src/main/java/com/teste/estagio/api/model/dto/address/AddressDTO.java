package com.teste.estagio.api.model.dto.address;

import java.util.UUID;

public record AddressDTO(
        UUID id,
        String street,
        String city,
        String state,
        String postalCode,
        String country
) {
}
