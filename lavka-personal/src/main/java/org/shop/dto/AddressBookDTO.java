package org.shop.dto;

import lombok.*;
import org.shop.db.persists.AddressBook;
import org.shop.enums.Status;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AddressBookDTO {

    private Long id;

    private Long clientId;

    private String country;

    private String city;

    private String street;

    private String houseNumber;

    private String apartmentOrOfficeNumber;

    private String comment;

    private String nameAddress;

    private String zipCode;

    private Status status;


    public AddressBookDTO(AddressBook addressBook) {
        setAll(addressBook);
    }


    public AddressBookDTO setAll(AddressBook addressBook) {
        this.id = addressBook.getId();
        this.clientId = addressBook.getClient().getId();
        this.country = addressBook.getCountry();
        this.city = addressBook.getCity();
        this.street = addressBook.getStreet();
        this.houseNumber = addressBook.getHouseNumber();
        this.apartmentOrOfficeNumber = addressBook.getApartmentOrOfficeNumber();
        this.comment = addressBook.getComment();
        this.nameAddress = addressBook.getNameAddress();
        this.zipCode = addressBook.getZipCode();
        this.status = addressBook.getStatus();
        return this;
    }
}
