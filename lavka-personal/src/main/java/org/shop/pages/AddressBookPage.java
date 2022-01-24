package org.shop.pages;

import lombok.*;
import org.shop.dto.AddressBookDTO;

import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class AddressBookPage extends DTOPage {

    private List<AddressBookDTO> addressList;

    @Builder
    public AddressBookPage(Integer pageNumber, Integer sizeOfPage, List<AddressBookDTO> addressList) {
        super(pageNumber, sizeOfPage);
        this.addressList = addressList;
    }
}
