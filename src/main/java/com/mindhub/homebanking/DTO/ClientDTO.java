package com.mindhub.homebanking.DTO;

import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Loans;

import java.util.List;
import java.util.stream.Collectors;

public class ClientDTO {
    private Long id;
    private String firstName, lastName, email;
    private List<AccountDTO> accountDTOS;
    private List<ClientsLoansDTO> clientsLoansDTOS;

    public ClientDTO(Client client) {
        id = client.getId();
        firstName = client.getFirstName();
        lastName = client.getLastname();
        email = client.getEmail();
        accountDTOS = client.getAccounts().stream().map(account1 -> new AccountDTO(account1)).collect(Collectors.toList());
        clientsLoansDTOS= client.getClientsLoans().stream().map(clientsLoans -> new ClientsLoansDTO(clientsLoans)).collect(Collectors.toList());

    }

    public List<ClientsLoansDTO> getClientsLoansDTOS() {
        return clientsLoansDTOS;
    }

    public List<AccountDTO> getAccountDTOS() {
        return accountDTOS;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }
}
