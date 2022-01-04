package org.shop.controllers;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.shop.dto.ClientDTO;
import org.shop.service.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

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

    @Operation(summary = "Get Client Info")
    @GetMapping("/client/{id}")
    public ClientDTO getClientInfo(@PathVariable("id") Long id) {
        Optional<ClientDTO> clientDTO = service.getClientInfo(id);
        return clientDTO.get();
    }

    `






}
