package org.shop.pages;

import lombok.*;
import org.shop.dto.ClientDTO;

import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class ClientsPage extends DTOPage {

    private List<ClientDTO> clientList;

    @Builder
    public ClientsPage(Integer pageNumber, Integer sizeOfPage, List<ClientDTO> clientList) {
        super(pageNumber, sizeOfPage);
        this.clientList = clientList;
    }



}
