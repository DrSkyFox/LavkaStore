package org.shop.service;

import org.shop.dto.AddressBookDTO;
import org.shop.dto.ClientDTO;

import java.util.List;

public interface IAddressBookService {

    AddressBookDTO createNewAddress(AddressBookDTO addressBook);

    AddressBookDTO updateAddress(AddressBookDTO addressBookDTO);

    List<AddressBookDTO> getAllAddressForClient(ClientDTO clientDTO);

    List<AddressBookDTO> getAllAddressForClientID(Long id);

    List<AddressBookDTO> getAllAddress();

    AddressBookDTO getAddressBookInfoById(Long id);

    Boolean deleteAddressBook(AddressBookDTO addressBook);

    Boolean deleteAddressBookById(Long id);

}
