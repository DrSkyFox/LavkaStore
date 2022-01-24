package org.shop.service;

import lombok.extern.slf4j.Slf4j;
import org.shop.db.ClientRepository;
import org.shop.dto.ClientDTO;
import org.shop.exceptions.BadRequestParameters;
import org.shop.pages.ClientsPage;
import org.shop.db.persists.Client;
import org.shop.enums.Status;
import org.shop.exceptions.ClientNotExists;
import org.shop.exceptions.ClientRequestBadParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

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


    @Override
    public List<ClientDTO> getAllClients() {
        log.info("Get All Clients");
        return repository.findAll().stream().map(ClientDTO::new).collect(Collectors.toList());
    }


    @Override
    public ClientsPage getAllClient(List<Status> statusList, Pageable pageable) {
        log.info("Get All Clients. Page - {}, size - {}, statusList - {}, sortField - {}, direction - {}",
                statusList, pageable.toString() );
        try {
            log.info("Get client from repository");
            List<ClientDTO> clients = repository.findAll(
                    statusList,
                    pageable).getContent().stream().map(ClientDTO::new).collect(Collectors.toList());
            log.info("Client List size {}", clients.size());
            return ClientsPage.builder()
                    .pageNumber(pageable.getPageNumber())
                    .sizeOfPage(pageable.getPageSize())
                    .clientList(clients)
                    .build();
        } catch (DataAccessException | IllegalArgumentException e ) {
            throw new BadRequestParameters("Something went wrong!", e.getCause());
        }
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

            if(client.getRegistrationDate() != clientDTO.getRegistrationDate()) {
                throw new ClientRequestBadParam("Registration date cant be change!");
            }

            client.setAll(clientDTO);
            log.info("Return saved data");
            clientDTO.setAll(repository.save(client));

            return clientDTO;
        } else {
            log.info("Save new client to DB");
            if(clientDTO.getRegistrationDate() != null) {
                throw new ClientRequestBadParam("Registration date cannot be set!");
            }
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
