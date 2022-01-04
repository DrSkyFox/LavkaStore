package org.shop.service;

import org.shop.dto.ClientDTO;
import org.shop.entites.Client;

import java.util.List;
import java.util.Optional;

public interface IClientService {

    Optional<ClientDTO> getClientInfo(Long id);

    List<ClientDTO> getAllClients();

    Optional<ClientDTO> saveClientData(ClientDTO clientDTO);

    void deleteClient(ClientDTO clientDTO);
}
