package org.shop.service;

import org.shop.dto.ClientDTO;
import org.shop.entites.Client;
import org.shop.enums.Status;
import org.shop.exceptions.ClientNotExists;
import org.shop.exceptions.ClientOperationException;
import org.shop.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientService implements IClientService{

    private final ClientRepository repository;

    @Autowired
    public ClientService(ClientRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    @Override
    public List<ClientDTO> getAllClients() {
        List<ClientDTO> clients = repository.findAll().stream().map(ClientDTO::new).collect(Collectors.toList());
        return clients;
    }

    @Transactional
    @Override
    public void deleteClient(ClientDTO clientDTO) {
        Optional<Client> client = repository.findById(clientDTO.getId());
        if(client.isPresent()) {
            client.get().setStatus(Status.DELETED);
            repository.save(client.get());
        } else throw new ClientNotExists();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<ClientDTO> getClientInfo(Long id) {
        Optional<ClientDTO> clientDTO =  repository.findById(id).map(ClientDTO::new);
        if (clientDTO.isEmpty()) throw new ClientNotExists();
        return clientDTO;
    }

    @Transactional
    @Override
    public Optional<ClientDTO> saveClientData(ClientDTO clientDTO) {
        if(clientDTO.getId() != null) {
            Optional<Client> client =repository.findById(clientDTO.getId());
            if(client.isPresent()) {
                return client.map(ClientDTO::new);
            } else throw new ClientOperationException();
        }
        return Optional.empty();
    }



}
