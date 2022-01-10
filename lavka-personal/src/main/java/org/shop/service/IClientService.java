package org.shop.service;

import org.shop.dto.ClientDTO;
import java.util.List;


public interface IClientService {

    ClientDTO getClientInfo(Long id);

    List<ClientDTO> getAllClients();

    ClientDTO saveClientData(ClientDTO clientDTO);

    ClientDTO changePhoneNumber(Integer phoneNumber, Long clientID);

    ClientDTO changeEmail(String email, Long clientID);

    void deleteClient(ClientDTO clientDTO);

    void deleteClient(Long id);
}
