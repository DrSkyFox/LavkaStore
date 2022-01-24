package org.shop.service;

import lombok.extern.slf4j.Slf4j;
import org.shop.db.persists.AddressBook;
import org.shop.db.persists.Client;
import org.shop.db.AddressBookRepositories;
import org.shop.db.ClientRepository;
import org.shop.dto.AddressBookDTO;
import org.shop.dto.ClientDTO;
import org.shop.enums.Status;
import org.shop.exceptions.AddressBookNotExists;
import org.shop.exceptions.AddressBookOperationException;
import org.shop.exceptions.BadRequestParameters;
import org.shop.exceptions.ClientNotExists;
import org.shop.pages.AddressBookPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AddressBookService implements IAddressBookService {

    private final AddressBookRepositories addressBookRepositories;
    private final ClientRepository clientRepository;

    @Autowired
    public AddressBookService(AddressBookRepositories addressBookRepositories, ClientRepository clientRepository) {
        this.addressBookRepositories = addressBookRepositories;
        this.clientRepository = clientRepository;
    }


    @Transactional
    @Override
    public AddressBookDTO createNewAddress(AddressBookDTO addressBook, Long clientId) {
        var client = clientRepository.findById(clientId);
        if(client.isEmpty()) {
            throw new ClientNotExists("Cant create address. Client with id - " + clientId + " not exists");
        }
        AddressBook addressToDB = new AddressBook(addressBook);
        addressToDB.setClient(client.get());
        log.info("Create new Address. Data: {}", addressBook.toString());
        AddressBook addressBookSaved = addressBookRepositories.save(addressToDB);
        log.info(" Saved Address. Data: {}", addressBookSaved);
        addressBook.setAll(addressBookSaved);
        return addressBook;
    }

    @Transactional
    @Override
    public AddressBookDTO updateAddress(AddressBookDTO addressBookDTO, Long clientId) {
        log.info("Update address book. Data: {}", addressBookDTO.toString());
        if (addressBookDTO.getId() == null) {
            log.warn("Id not found");
            throw new AddressBookOperationException("Wrong Request Operation. Id is null !!");
        }

        Optional<AddressBook> addressBook = addressBookRepositories.findById(addressBookDTO.getId());

        if (addressBook.isEmpty()) {
            log.warn("Update address book. Address not found");
            throw new AddressBookNotExists("Address not exists with id - " + addressBookDTO.getId());
        }

        AddressBook addressBookFromBD = addressBook.get();

        if(addressBookFromBD.getClient().getId() != clientId) {
            log.warn("AddressBook. Client id not equals in request. AddressFromBD ClientID - {}, AddressBookDTO ClientID - ", addressBookFromBD.getClient().getId(), addressBookDTO.getClientId());
            throw new AddressBookOperationException("AddressBook. Client id not equals in request");
        }

        try {
            log.info("Update address book. Save Data to DB");
            addressBookFromBD = addressBookRepositories.save(addressBookFromBD.setAll(addressBookDTO));
            addressBookDTO.setAll(addressBookFromBD);
        } catch (DataAccessException | IllegalArgumentException e) {
            log.warn("Update address book. Exception - {}", e.getMessage());
            throw new AddressBookOperationException("Something wrong", e);
        }
        return null;
    }

    @Override
    public AddressBookPage getAllAddressPage(List<Status> statusList, Pageable pageable) {
        log.info("Get All Address Page. Params: status - {}, Pagaable: page - , size - , sort - , order - ", statusList, pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());
        try {
            var addressList = addressBookRepositories
                    .findAllByStatusFilter(
                            statusList, pageable)
                    .stream()
                    .map(AddressBookDTO::new)
                    .collect(Collectors.toList());
            log.info("Return Address Book Page - {}", addressList);
            return AddressBookPage.builder()
                    .addressList(addressList)
                    .pageNumber(pageable.getPageNumber())
                    .sizeOfPage(pageable.getPageSize())
                    .build();
        } catch (DataAccessException | IllegalArgumentException e) {
            log.warn("Get All Address Page. Exception - {}", e.getMessage());
            throw new BadRequestParameters("Something went wrong!", e.getCause());
        }
    }

    @Override
    public AddressBookPage getAllAddressPageByClient(Long clientId , List<Status> statusList, Pageable pageable) {
        log.info("Get All Address Page. Params: status - {}, ClientId - {}. Pagaable: page - , size - , sort - , order - ", clientId, statusList, pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort());
        try {
            var addressList = addressBookRepositories
                    .findAllByClientAndStatusFilter(
                            clientId,
                            statusList,
                            pageable
                    )
                    .stream()
                    .map(AddressBookDTO::new)
                    .collect(Collectors.toList());
            log.info("Return Address Book Page - {}", addressList);
            return AddressBookPage.builder()
                    .addressList(addressList)
                    .pageNumber(pageable.getPageNumber())
                    .sizeOfPage(pageable.getPageSize())
                    .build();
        } catch (DataAccessException | IllegalArgumentException e) {
            log.warn("Get All Address Page. Exception - {}", e.getMessage());
            throw new BadRequestParameters("Something went wrong!", e.getCause());
        }
    }



    @Transactional(readOnly = true)
    @Override
    public AddressBookDTO getAddressBookInfoById(Long id) {
        log.info("Get Address Info with id - {}", id);
        return addressBookRepositories.findById(id).map(AddressBookDTO::new).orElseThrow(AddressBookNotExists::new);
    }

    @Override
    public Boolean deleteAddressBookByIdForClient(Long clientId, Long addressId) {

        var address = addressBookRepositories.findById(addressId);

        if(address.isEmpty()) {
            throw new AddressBookNotExists("Address not exists with Id" + addressId + "for Client Id " + addressId);
        }

        address.get().setStatus(Status.DELETED);
        addressBookRepositories.save(address.get());
        return true;

    }

    @Transactional
    @Override
    public Boolean deleteAddressBookById(Long id) {
        return deleteAddressBook(id);
    }

    private Boolean deleteAddressBook(Long id) {
        log.info("Delete Address - {}", id);
        var addressBookFromBD = addressBookRepositories.findById(id);
        if (addressBookFromBD.isPresent()) {
            log.info("Address found. Do delete");
            addressBookFromBD.get().setStatus(Status.DELETED);
            addressBookRepositories.save(addressBookFromBD.get());
            log.info("Address status changed to deleted");
            return true;
        } else {
            log.warn("Address not found");
            throw new AddressBookNotExists();
        }
    }
}
