package org.shop.controllers;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.shop.dto.ClientDTO;
import org.shop.responses.ResponseDTO;
import org.shop.responses.ResponseStatus;
import org.shop.service.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


@Slf4j
@Tag(name = "Client API")
@RestController
@RequestMapping("/api/v1/client")
public class ClientController {

    private IClientService service;

    @Autowired
    public ClientController(IClientService service) {
        this.service = service;
    }

    @Operation(summary = "Get All Clients")
    @GetMapping("/all")
    public List<ClientDTO> getAllClients() {
        log.info("Get All clients");
        return service.getAllClients();
    }

    @PreAuthorize("hasAnyRole('CUSTOMER')")
    @Operation(summary = "Get Client Info. Minimal Role Customer")
    @GetMapping("/client/{id}")
    public ResponseEntity<ClientDTO> getClientInfo(@PathVariable("id") Long id) {
        ClientDTO clientDTO = service.getClientInfo(id);
        return new ResponseEntity<>(clientDTO, HttpStatus.OK);
    }

    @Operation(summary = "Update Client Info")
    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("/client/update")
    public ResponseEntity<ClientDTO> setClientInfo(@RequestBody ClientDTO clientDTO) {
        clientDTO = service.saveClientData(clientDTO);
        return new ResponseEntity<>(clientDTO, HttpStatus.OK);
    }

    @Operation(summary = "Update Client Email. Minimal Role Manager")
    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping("/client/update/email")
    public ResponseEntity<Object> setChangeEmail(@RequestParam("mail") String mail, @RequestParam("id") Long idClient) {
        service.changeEmail(mail, idClient);
        return new ResponseEntity<>(new ResponseDTO(new Date(), "Email change successful", ResponseStatus.SUCCESS),HttpStatus.OK);
    }

    @Operation(summary = "Update Client PhoneNumber. Minimal Role Manager")
    @PreAuthorize("hasRole('MANAGER') ")
    @PostMapping("/client/update/email")
    public ResponseEntity<Object> setPhoneNumber(@RequestParam("phoneNumber") Integer phoneNumber, @RequestParam("id") Long idClient) {
        service.changePhoneNumber(phoneNumber, idClient);
        return new ResponseEntity<>(new ResponseDTO(new Date(), "PhoneNumber change successful", ResponseStatus.SUCCESS),HttpStatus.OK);
    }

    @Operation(summary = "Update Client PhoneNumber. Minimal Role Manager")
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @PostMapping("/client/delete")
    public ResponseEntity<Object> deleteClientById(@RequestParam("id") Long idClient) {
        service.deleteClient(idClient);
        return new ResponseEntity<>(new ResponseDTO(new Date(), "Client delete successful", ResponseStatus.SUCCESS),HttpStatus.OK);
    }

    @Operation(summary = "Update Client PhoneNumber. Minimal Role Manager")
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @PostMapping("/client/delete")
    public ResponseEntity<Object> deleteClient(@RequestBody ClientDTO clientDTO) {
        service.deleteClient(clientDTO);
        return new ResponseEntity<>(new ResponseDTO(new Date(), "Client delete successful", ResponseStatus.SUCCESS),HttpStatus.OK);
    }

}
