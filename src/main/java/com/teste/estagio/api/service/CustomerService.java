package com.teste.estagio.api.service;

import com.teste.estagio.api.exception.AddressNotFoundException;
import com.teste.estagio.api.exception.DuplicateFieldException;
import com.teste.estagio.api.exception.CustomerNotFoundException;
import com.teste.estagio.api.model.Address;
import com.teste.estagio.api.model.Customer;
import com.teste.estagio.api.model.dto.customer.CustomerCreateRequestDTO;
import com.teste.estagio.api.model.dto.customer.CustomerResponseDTO;
import com.teste.estagio.api.model.dto.customer.CustomerUpdateRequestDTO;
import com.teste.estagio.api.repository.CustomerRepository;
import com.teste.estagio.api.utils.CPFFormatterUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Consumer;


@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public CustomerService(CustomerRepository customerRepository, BCryptPasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(UUID customerId) {
        return customerRepository.findById(customerId).orElseThrow(
                () -> new CustomerNotFoundException("Customer not found or does not exist")
        );
    }

    public CustomerResponseDTO createCustomer(CustomerCreateRequestDTO customerDTO) {
        Customer customer = new Customer();
        customer.setName(customerDTO.name());
        customer.setLastName(customerDTO.lastName());
        customer.setEmail(customerDTO.email());
        customer.setPassword(customerDTO.password());
        encodedPassword(customer);
        customer.setPhone(customerDTO.phone());
        customer.setBirthDate(customerDTO.birthDate());
        customer.setCpf(CPFFormatterUtils.cleanCPF(customerDTO.cpf()));


        List<Address> addresses = customerDTO.addresses().stream()
                .map(addressDTO -> {
                    Address address = new Address();
                    address.setStreet(addressDTO.street());
                    address.setPostalCode(addressDTO.postalCode());
                    address.setCity(addressDTO.city());
                    address.setState(addressDTO.state());
                    address.setCountry(addressDTO.country());
                    address.setCustomer(customer);
                    return address;
                }).toList();

        customer.setAddresses(addresses);
        validateUniqueEmail(customer.getEmail());
        validateUniqueCPF(customer.getCpf());
        encodedPassword(customer);
        Customer savedCustomer = customerRepository.save(customer);
        return new CustomerResponseDTO(savedCustomer);
    }

    public CustomerResponseDTO updateCustomer(UUID customerId, CustomerUpdateRequestDTO updateDTO) {
        Customer existingCustomer = getCustomerById(customerId);

        updatedField(updateDTO.name(), existingCustomer.getName(), existingCustomer::setName);
        updatedField(updateDTO.lastName(), existingCustomer.getLastName(), existingCustomer::setLastName);
        updatedEmail(updateDTO, existingCustomer);
        updatedPassword(updateDTO, existingCustomer);

        if (Objects.nonNull(updateDTO.addresses())) {
            updateDTO.addresses().forEach(addressDTO -> {
                if (Objects.isNull(addressDTO.id())) {
                    throw new AddressNotFoundException("ID do endereço é obrigatório");
                }

                Address existingAddress = existingCustomer.getAddresses().stream()
                        .filter(a -> a.getId().equals(addressDTO.id()))
                        .findFirst()
                        .orElseThrow(() -> new AddressNotFoundException("Endereço não encontrado"));

                updatedField(addressDTO.street(), existingAddress.getStreet(), existingAddress::setStreet);
                updatedField(addressDTO.city(), existingAddress.getCity(), existingAddress::setCity);
                updatedField(addressDTO.state(), existingAddress.getState(), existingAddress::setState);
                updatedField(addressDTO.postalCode(), existingAddress.getPostalCode(), existingAddress::setPostalCode);
                updatedField(addressDTO.country(), existingAddress.getCountry(), existingAddress::setCountry);

                existingAddress.setCustomer(existingCustomer);
            });
        }

        Customer updatedCustomer = customerRepository.save(existingCustomer);
        return new CustomerResponseDTO(updatedCustomer);
    }

    public void deleteCustomer(UUID id) {

        if (Objects.isNull(getCustomerById(id)))
            throw new CustomerNotFoundException("Customer not found or does not exist");

        customerRepository.deleteById(id);
    }

    private <T> void updatedField(T newValue, T currentValue, Consumer<T> setter) {
        if (Objects.nonNull(newValue) && !Objects.equals(newValue, currentValue))
            setter.accept(newValue);
    }

    private void updatedEmail(CustomerUpdateRequestDTO customerUpdateRequestDTO, Customer existingCustomer) {
        if (Objects.nonNull(customerUpdateRequestDTO.email()) && !Objects.equals(customerUpdateRequestDTO.email(), existingCustomer.getEmail())) {

            validateUniqueEmail(existingCustomer.getEmail());
            existingCustomer.setEmail(customerUpdateRequestDTO.email());
        }
    }

    private void updatedPassword(CustomerUpdateRequestDTO updateCustomer, Customer existingCustomer) {
        if (Objects.nonNull(updateCustomer.password())) {
            if (!passwordEncoder.matches(updateCustomer.password(), existingCustomer.getPassword()))
                existingCustomer.setPassword(passwordEncoder.encode(updateCustomer.password()));
        }
    }

    private void encodedPassword(Customer customer) {
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
    }

    private void validateUniqueCPF(String cpf) {

        if (customerRepository.existsByCpf(cpf))
            throw new DuplicateFieldException("CPF already in use!");
    }

    private void validateUniqueEmail(String email) {

        if (customerRepository.existsByEmail(email))
            throw new DuplicateFieldException("E-mail already in use!");
    }
}
