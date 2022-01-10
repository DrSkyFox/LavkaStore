package org.shop.service;

import lombok.extern.slf4j.Slf4j;
import org.shop.dto.ClientDTO;
import org.shop.entites.Client;
import org.shop.enums.Status;
import org.shop.exceptions.ClientNotExists;
import org.shop.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ClientService implements IClientService {

    private final ClientRepository repository;

    @Autowired
    public ClientService(ClientRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    @Override
    public List<ClientDTO> getAllClients() {
        log.info("Get All Clients");
        return repository.findAll().stream().map(ClientDTO::new).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void deleteClient(ClientDTO clientDTO) {
        log.info("Delete client - {}", clientDTO.toString());
        deleteClient(clientDTO.getId());
    }

    @Transactional
    @Override
    public void deleteClient(Long id) {
        log.info("Delete client with id - {}", id);
        Optional<Client> client = repository.findById(id);
        if (client.isPresent()) {
            log.info("Change client status on deleted");
            client.get().setStatus(Status.DELETED);
            repository.save(client.get());
        } else {
            log.warn("Client with id - {} not found", id);
            throw new ClientNotExists();
        }
    }

    @Transactional(readOnly = true)
    @Override
    public ClientDTO getClientInfo(Long id) {
        log.info("Get Client Info with id - {} ",id );
        return repository.findById(id).map(ClientDTO::new).orElseThrow(ClientNotExists::new);
    }

    @Transactional
    @Override
    public ClientDTO saveClientData(ClientDTO clientDTO) {
        log.info("Save client {}, client id is - {}", clientDTO.toString(), clientDTO.getId());
        Client client;
        if (clientDTO.getId() != null) {
            log.info("Update client data");
            client = repository.findById(clientDTO.getId()).orElseThrow(ClientNotExists::new);
            client.setAll(clientDTO);
            log.info("Return saved data");
            clientDTO.setAll(repository.save(client));
            return clientDTO;
        } else {
            log.info("Save new client to DB");
            client = repository.save(new Client(clientDTO));
            return clientDTO.setAll(client);
        }
    }


    @Transactional
    @Override
    public ClientDTO changePhoneNumber(Integer phoneNumber, Long clientID) {
        log.info("Change phoneNumber to {} for client {}", phoneNumber, clientID);
        Optional<Client> client = repository.findById(clientID);
        if (client.isPresent()) {
            client.get().setPhoneNumber(phoneNumber);
            log.info("Client found. Save to DB and return updated data");
            return new ClientDTO(repository.save(client.get()));
        } else throw new ClientNotExists("Client not found with id: " + clientID);
    }


    @Transactional
    @Override
    public ClientDTO changeEmail(String email, Long clientID) {
        log.info("Change email - {} for client {}", email, clientID);
        Optional<Client> client = repository.findById(clientID);
        if (client.isPresent()) {
            client.get().setEmail(email);
            log.info("Client found. Save to DB and return updated data");
            return new ClientDTO(repository.save(client.get()));
        } else throw new ClientNotExists("Client not found with id: " + clientID);

    }



}
