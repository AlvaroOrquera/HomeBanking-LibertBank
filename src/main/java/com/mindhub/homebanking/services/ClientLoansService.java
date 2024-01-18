package com.mindhub.homebanking.services;

import com.mindhub.homebanking.models.ClientsLoans;

public interface ClientLoansService {
    ClientsLoans findById(Long id);
    void saveClientsLoans(ClientsLoans clientsLoans);
}
