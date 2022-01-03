package org.shop.entites;

import lombok.*;
import org.shop.enums.Status;

import javax.persistence.*;

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

}
