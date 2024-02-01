package com.mindhub.homebanking.DTO;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.AccountType;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AccountDTO {

    private Long id;
    private String number;
    private LocalDate createDate;
    private double balance;
    private AccountType accountType;
    private Set<TransactionDTO> transactions;

    public AccountDTO(Account account) {
        id = account.getId();
        number = account.getNumber();
        createDate = account.getCreateDate();
        balance = account.getBalance();
        accountType = account.getAccountType();
        transactions = account.getTransactions().stream()
                .filter(accountDTO -> account.isActive())
                .map(transaction -> new TransactionDTO(transaction)).collect(Collectors.toSet());
    }

    public Long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public double getBalance() {
        return balance;
    }

    public Set<TransactionDTO> getTransactions() {
        return transactions;
    }

    public AccountType getAccountType() {
        return accountType;
    }
}
