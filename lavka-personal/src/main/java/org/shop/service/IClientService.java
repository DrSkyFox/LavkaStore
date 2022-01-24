package org.shop.service;

import org.shop.dto.ClientDTO;
import org.shop.pages.ClientsPage;
import org.shop.enums.Status;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface IClientService {

    ClientDTO getClientInfo(Long id);

    List<ClientDTO> getAllClients();

    ClientDTO saveClientData(ClientDTO clientDTO);

    ClientDTO changePhoneNumber(Integer phoneNumber, Long clientID);

    ClientDTO changeEmail(String email, Long clientID);

    void deleteClient(Long id);

    ClientsPage getAllClient(List<Status> statusList, Pageable pageable);

}
