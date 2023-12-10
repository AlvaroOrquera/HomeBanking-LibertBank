package com.mindhub.homebanking.DTO;

import com.mindhub.homebanking.models.Account;

import java.time.LocalDate;

public class AccountDTO {

    private Long id;
    private String number;
    private LocalDate createDate;
    private double balance;

    public AccountDTO(Account account){
        id = account.getId();
        number = account.getNumber();
        createDate = account.getCreateDate();
        balance = account.getBalance();
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
}
