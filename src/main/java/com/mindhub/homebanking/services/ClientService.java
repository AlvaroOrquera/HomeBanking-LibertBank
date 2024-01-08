package com.mindhub.homebanking.services;

import com.mindhub.homebanking.DTO.ClientDTO;
import com.mindhub.homebanking.models.Client;

import java.util.List;


public interface ClientService {
    List<ClientDTO> getAllClientDTO();

    List<Client> getAllClient();

    Client getAuthenticatedClient(String email);

    void saveClient(Client client);

    boolean existsByEmail(String email);
}
