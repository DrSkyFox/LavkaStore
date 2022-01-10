package org.shop.service;

import lombok.extern.slf4j.Slf4j;
import org.shop.dto.AddressBookDTO;
import org.shop.dto.ClientDTO;
import org.shop.entites.AddressBook;
import org.shop.entites.Client;
import org.shop.enums.Status;
import org.shop.exceptions.AddressBookNotExists;
import org.shop.exceptions.AddressBookOperationException;
import org.shop.exceptions.ClientNotExists;
import org.shop.repositories.AddressBookRepositories;
import org.shop.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import java.util.stream.Collectors;

@Slf4j
@Service
public class AddressBookService implements IAddressBookService{

    private final AddressBookRepositories addressBookRepositories;
    private final ClientRepository clientRepository;

    @Autowired
    public AddressBookService(AddressBookRepositories addressBookRepositories, ClientRepository clientRepository) {
        this.addressBookRepositories = addressBookRepositories;
        this.clientRepository = clientRepository;
    }


    @Transactional
    @Override
    public AddressBookDTO createNewAddress(AddressBookDTO addressBook) {
        log.info("Create new Address. Data: {}", addressBook.toString());
        AddressBook addressBookSaved = addressBookRepositories.save(new AddressBook(addressBook));
        log.info(" Saved Address. Data: {}", addressBookSaved);
        addressBook.setAll(addressBookSaved);
        return addressBook;
    }

    @Transactional
    @Override
    public AddressBookDTO updateAddress(AddressBookDTO addressBookDTO) {
        log.info("Update address book. Data: {}",  addressBookDTO.toString());
        if(addressBookDTO.getId() == null) {
            log.warn("Id not found");
            throw new AddressBookOperationException("Wrong Request Operation. Id is null !!");
        }
        Optional<AddressBook> addressBook = addressBookRepositories.findById(addressBookDTO.getId());
        if(addressBook.isEmpty()) {
            log.warn("Address not found");
            throw new AddressBookNotExists("Address not exists with id - " + addressBookDTO.getId());
        }
        AddressBook addressBookFromBD = addressBook.get();
        try {
            log.info(" Save Data to DB");
            addressBookFromBD = addressBookRepositories.save(addressBookFromBD.setAll(addressBookDTO));
            addressBookDTO.setAll(addressBookFromBD);
        } catch (IllegalArgumentException e) {
            log.warn("Error {}", e.getMessage());
            throw new AddressBookOperationException("Something wrong", e);
        }
        return null;
    }



    @Transactional(readOnly = true)
    @Override
    public List<AddressBookDTO> getAllAddress() {
        log.info("Get All Address");
        return addressBookRepositories.findAll().stream().map(AddressBookDTO::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public AddressBookDTO getAddressBookInfoById(Long id) {
        log.info("Get Address Info with id - {}", id);
        return addressBookRepositories.findById(id).map(AddressBookDTO::new).orElseThrow(AddressBookNotExists::new);
    }

    @Transactional(readOnly = true)
    @Override
    public List<AddressBookDTO> getAllAddressForClient(ClientDTO clientDTO) {
        log.info("Get All Address of Client - {}", clientDTO);
        return getAllAddressForClientId(clientDTO.getId());
    }


    @Override
    public List<AddressBookDTO> getAllAddressForClientID(Long id) {
        log.info("Get All Address of Client - {}",id);
        return getAllAddressForClientId(id);
    }


    private List<AddressBookDTO> getAllAddressForClientId(Long id) {
        log.info("Check exists client");
        Optional<Client> client = clientRepository.findById(id);
        if(client.isPresent()) {
            log.info("Get All Address");
            return addressBookRepositories.findAllByClient(client.get()).stream().map(AddressBookDTO::new).collect(Collectors.toList());
        } else {
            log.warn("Client with id - {} not found", id);
            throw new ClientNotExists("Client with id " + id + " not exists");
        }
    }

    @Transactional
    @Override
    public Boolean deleteAddressBook(AddressBookDTO addressBook) {
        log.info("Delete Address - {}",  addressBook);
        return deleteAddressBook(addressBook.getId());
    }

    @Transactional
    @Override
    public Boolean deleteAddressBookById(Long id) {
        return deleteAddressBook(id);
    }

    private Boolean deleteAddressBook(Long id) {
        log.info("Delete Address - {}", id);
        Optional<AddressBook> addressBookFromBD = addressBookRepositories.findById(id);
        if (addressBookFromBD.isPresent()) {
            log.info("Address found. Do delete");
            addressBookFromBD.get().setStatus(Status.DELETED);
            addressBookRepositories.save(addressBookFromBD.get());
            log.info("Address deleted");
            return true;
        } else {
            log.warn("Address not found");
            throw new AddressBookNotExists();
        }
    }
}
