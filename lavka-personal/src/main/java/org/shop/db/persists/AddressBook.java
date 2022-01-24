package org.shop.db.persists;

import lombok.*;
import org.shop.dto.AddressBookDTO;
import org.shop.enums.Status;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "address_book")
public class AddressBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @Column(name = "country")
    private String country;

    @Column(name = "city")
    private String city;

    @Column(name = "street")
    private String street;

    @Column(name = "houseNumber")
    private String houseNumber;

    @Column(name = "appartment_office_number")
    private String apartmentOrOfficeNumber;

    @Column(name = "comment")
    private String comment;

    @Column(name = "nameAddress", nullable = false)
    private String nameAddress;

    @Column(name = "zipCode")
    private String zipCode;

    @Enumerated(EnumType.ORDINAL)
    private Status status;

    public AddressBook(AddressBookDTO addressBook) {
        setAll(addressBook);
    }

    public AddressBook setAll(AddressBookDTO addressBook) {
        this.id = addressBook.getId();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddressBook book = (AddressBook) o;
        return Objects.equals(id, book.id) && Objects.equals(client, book.client) && Objects.equals(country, book.country) && Objects.equals(city, book.city) && Objects.equals(street, book.street) && Objects.equals(houseNumber, book.houseNumber) && Objects.equals(apartmentOrOfficeNumber, book.apartmentOrOfficeNumber) && Objects.equals(comment, book.comment) && Objects.equals(nameAddress, book.nameAddress) && Objects.equals(zipCode, book.zipCode) && status == book.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, client, country, city, street, houseNumber, apartmentOrOfficeNumber, comment, nameAddress, zipCode, status);
    }
}
