package com.mindhub.homebanking.DTO;

import com.mindhub.homebanking.models.Account;

public class CreateTransactionsDTO {
    double amount, currentBalance;
    String originAccount,destinationAccount,description;

    public double getCurrentBalance() {
        return currentBalance;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getOriginAccount() {
        return originAccount;
    }

    public void setOriginAccount(String originAccount) {
        this.originAccount = originAccount;
    }

    public String getDestinationAccount() {
        return destinationAccount;
    }

    public void setDestinationAccount(String destinationAccount) {
        this.destinationAccount = destinationAccount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
