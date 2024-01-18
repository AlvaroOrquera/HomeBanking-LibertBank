package com.mindhub.homebanking.DTO;

import java.util.List;

public class LoanAplicationDTO {
    private Long id;

    private double amount;
    private Integer payments;
    private String destinationAccount;

    public Long getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public Integer getPayments() {
        return payments;
    }

    public String getDestinationAccount() {
        return destinationAccount;
    }

}
