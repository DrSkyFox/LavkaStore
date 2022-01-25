package org.shop.controllers;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.shop.dto.ClientDTO;
import org.shop.enums.Status;
import org.shop.pages.ClientsPage;
import org.shop.service.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;



@Slf4j
@Tag(name = "Client API")
@RestController
@RequestMapping(value = "/api/v1/client", produces = MediaType.APPLICATION_JSON_VALUE)
public class ClientController {

    private final IClientService service;

    @Autowired
    public ClientController(IClientService service) {
        this.service = service;
    }

    @Operation(summary = "Get All Clients")
    @GetMapping("/all")
    public ResponseEntity<ClientsPage> getAllClients() {
        log.info("Get All clients");
        return ResponseEntity.status(HttpStatus.OK).body(ClientsPage.builder().clientList( service.getAllClients()).build());
    }

    @Operation(summary = "Get All Clients")
    @GetMapping(value = "/all", params = {"page" ,"size", "status", "sortField", "direction"})
    public ClientsPage getAllClientsPage(@PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
                                         @RequestParam(name = "status", required = false, defaultValue = "ACTIVE, DELETED") List<Status> status) {
        log.info("Get All clients");
        return service.getAllClient(status, pageable);
    }


    @PreAuthorize("hasAnyRole('CUSTOMER')")
    @Operation(summary = "Get Client Info. Minimal Role Customer")
    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> getClientInfo(@PathVariable("id") Long id) {
        ClientDTO clientDTO = service.getClientInfo(id);
        return ResponseEntity.status(HttpStatus.OK).body(clientDTO);
    }

    @Operation(summary = "Update Client Info")
    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("/update")
    public ResponseEntity<ClientDTO> setClientInfo(@RequestBody ClientDTO clientDTO) {
        clientDTO = service.saveClientData(clientDTO);
        return ResponseEntity.status(HttpStatus.OK).body(clientDTO);
    }

    @Operation(summary = "Update Client Email. Minimal Role Manager")
    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping("/{id}/update/email")
    public ResponseEntity<ClientDTO> setChangeEmail(@PathVariable("id") Long idClient, @RequestParam("email") String email) {
        ClientDTO clientDTO = service.changeEmail(email, idClient);
        return ResponseEntity.status(HttpStatus.OK).body(clientDTO);
    }

    @Operation(summary = "Update Client PhoneNumber. Minimal Role Manager")
    @PreAuthorize("hasRole('MANAGER') ")
    @PostMapping("/{id}/update/phone")
    public ResponseEntity<Object> setPhoneNumber(@PathVariable("id") Long idClient, @RequestParam("phone") Integer phoneNumber) {
        ClientDTO clientDTO = service.changePhoneNumber(phoneNumber, idClient);
        return ResponseEntity.status(HttpStatus.OK).body(clientDTO);
    }

    @Operation(summary = "Update Client PhoneNumber. Minimal Role Manager")
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Object> deleteClientById(@PathVariable("id") Long idClient) {
        service.deleteClient(idClient);
        return ResponseEntity.status(HttpStatus.OK).body(new Date());
    }


}
