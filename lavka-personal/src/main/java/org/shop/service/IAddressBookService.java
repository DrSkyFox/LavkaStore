package org.shop.service;

import org.shop.db.persists.AddressBook;
import org.shop.dto.AddressBookDTO;
import org.shop.dto.ClientDTO;
import org.shop.enums.Status;
import org.shop.pages.AddressBookPage;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IAddressBookService {

    AddressBookDTO createNewAddress(AddressBookDTO addressBook, Long clientId);

    AddressBookDTO updateAddress(AddressBookDTO addressBookDTO, Long clientId);


    AddressBookPage getAllAddressPage(List<Status> statusList, Pageable pageable);

    AddressBookPage getAllAddressPageByClient(Long clientId, List<Status> statusList, Pageable pageable);

    AddressBookDTO getAddressBookInfoById(Long id);

    Boolean deleteAddressBookById(Long id);

    Boolean deleteAddressBookByIdForClient(Long clientId, Long addressId) ;

}
