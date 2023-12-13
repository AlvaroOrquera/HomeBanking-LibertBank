package com.mindhub.homebanking.DTO;

import com.mindhub.homebanking.models.ClientsLoans;

public class ClientsLoansDTO {
    private Long id;
    private Long loansid;
    private String name;
    private double amount;
    private Integer payments;

    public ClientsLoansDTO(ClientsLoans clientsLoans) {
        id = clientsLoans.getId();
        loansid = clientsLoans.getLoans().getId();
        name = clientsLoans.getLoans().getName();
        amount = clientsLoans.getAmount();
        payments = clientsLoans.getPayments();
    }

    public Long getId() {
        return id;
    }
    public Long getLoansid() {
        return loansid;
    }

    public void setLoansid(Long loansid) {
        this.loansid = loansid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Integer getPayments() {
        return payments;
    }

    public void setPayments(Integer payments) {
        this.payments = payments;
    }
}
