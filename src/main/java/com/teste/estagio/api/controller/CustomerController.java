package com.teste.estagio.api.controller;

import com.teste.estagio.api.model.Customer;
import com.teste.estagio.api.model.dto.customer.CustomerCreateRequestDTO;
import com.teste.estagio.api.model.dto.customer.CustomerResponseDTO;
import com.teste.estagio.api.model.dto.customer.CustomerUpdateRequestDTO;
import com.teste.estagio.api.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Customer", description = "Contém todas as operações relativas aos clientes")
@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Operation(summary = "Buscar todos os clientes", description = "Recurso para retornar todos os clientes",
    responses = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerResponseDTO.class)))
    })
    @GetMapping("/get-all")
    public ResponseEntity<List<CustomerResponseDTO>> findAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        List<CustomerResponseDTO> customerResponseDTOS = customers.stream().map(CustomerResponseDTO::new)
                .toList();
        return ResponseEntity.ok(customerResponseDTOS);
    }

    @Operation(summary = "Buscar um cliente por ID", description = "Recurso para retornar todos os cursos",
    responses = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerResponseDTO.class)))
    })
    @GetMapping("/get-id/{id}")
    public ResponseEntity<CustomerResponseDTO> findCustomerById(@PathVariable UUID id) {
        Customer customer = customerService.getCustomerById(id);
        CustomerResponseDTO customerDTO = new CustomerResponseDTO(customer);
        return ResponseEntity.ok(customerDTO);
    }

    @Operation(summary = "Cadastrar um cliente", description = "Recurso para cadastrar um cliente",
    responses = {
            @ApiResponse(responseCode = "201", description = "Cliente cadastrado com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerResponseDTO.class)))
    })
    @PostMapping("/create")
    public ResponseEntity<CustomerResponseDTO> createCustomer(@RequestBody @Valid CustomerCreateRequestDTO createCustomerDTO) {
        CustomerResponseDTO createdCustomer = customerService.createCustomer(createCustomerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCustomer);
    }

    @Operation(summary = "Atualizar um cliente", description = "Recurso para atualizar os dados de um cliente, " +
            "atualizando somente o que ele inserir para ser atualizado e mantendo o que já existe",
    responses = {
            @ApiResponse(responseCode = "201", description = "Dados do cliente atualizados com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerResponseDTO.class)))
    })
    @PutMapping("/update/{id}")
    public ResponseEntity<CustomerResponseDTO> updateCustomer(@PathVariable UUID id, @RequestBody @Valid CustomerUpdateRequestDTO customerUpdateDTO) {
        CustomerResponseDTO updatedCustomerDTO = customerService.updateCustomer(id, customerUpdateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedCustomerDTO);
    }

    @Operation(summary = "Deletar um cliente", description = "Recurso para deletar um cliente",
    responses = {
            @ApiResponse(responseCode = "204", description = "Cliente deletado com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerResponseDTO.class)))
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable UUID id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}
