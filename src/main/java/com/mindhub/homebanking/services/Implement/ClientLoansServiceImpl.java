package com.mindhub.homebanking.services.Implement;

import com.mindhub.homebanking.models.ClientsLoans;
import com.mindhub.homebanking.repositories.ClientsLoansRepository;
import com.mindhub.homebanking.services.ClientLoansService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientLoansServiceImpl implements ClientLoansService {
    @Autowired
    ClientsLoansRepository clientsLoansRepository;
    @Override
    public void saveClientsLoans(ClientsLoans clientsLoans) {
        clientsLoansRepository.save(clientsLoans);

    }
}
