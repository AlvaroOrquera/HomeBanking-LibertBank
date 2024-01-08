package com.mindhub.homebanking.DTO;

import java.util.List;

public class LoanAplicationDTO {
    private Long id;

    private Double amount;
    private Integer payments;
    private String destinationAccount;

    public Long getId() {
        return id;
    }

    public Double getAmount() {
        return amount;
    }

    public Integer getPayments() {
        return payments;
    }

    public String getDestinationAccount() {
        return destinationAccount;
    }
}
