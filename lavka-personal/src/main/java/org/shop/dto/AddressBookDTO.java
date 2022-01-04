package org.shop.dto;

import lombok.*;
import org.shop.entites.AddressBook;
import org.shop.entites.Client;
import org.shop.enums.Status;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressBookDTO {

    private Long id;

    private Client client;
    private Long clientID;


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
        this.id = addressBook.getId();
        this.client = addressBook.getClient();
        this.country = addressBook.getCountry();
        this.city = addressBook.getCity();
        this.street = addressBook.getStreet();
        this.houseNumber = addressBook.getHouseNumber();
        this.apartmentOrOfficeNumber = addressBook.getApartmentOrOfficeNumber();
        this.comment = addressBook.getComment();
        this.nameAddress = addressBook.getNameAddress();
        this.zipCode = addressBook.getZipCode();
        this.status = addressBook.getStatus();
    }

}
