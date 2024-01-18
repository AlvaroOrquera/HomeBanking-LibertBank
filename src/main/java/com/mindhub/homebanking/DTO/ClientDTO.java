package com.mindhub.homebanking.DTO;

import com.mindhub.homebanking.models.*;

import java.util.List;
import java.util.stream.Collectors;

public class ClientDTO {
    private Long id;
    private String firstName, lastName, email;
    private RoleType roleType;
    private List<AccountDTO> accountDTOS;
    private List<ClientsLoansDTO> clientsLoansDTOS;
    private List<CardDTO> cardDTOS;

    public ClientDTO(Client client) {
        id = client.getId();
        firstName = client.getFirstName();
        lastName = client.getLastName();
        email = client.getEmail();
        roleType = client.getRol();
        accountDTOS = client.getAccounts().stream()
                .filter(Account::isActive)
                .map(account1 -> new AccountDTO(account1))
                .collect(Collectors.toList());
        clientsLoansDTOS = client.getClientsLoans().stream()
                .map(clientsLoans -> new ClientsLoansDTO(clientsLoans))
                .collect(Collectors.toList());
        cardDTOS = client.getCards().stream()
                .filter(card -> card.getStatus().equals(CardStatus.ACTIVE))
                .map(card -> new CardDTO(card))
                .collect(Collectors.toList());
    }

    public List<CardDTO> getCardDTOS() {
        return cardDTOS;
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

    public RoleType getRoleType() {
        return roleType;
    }
}
