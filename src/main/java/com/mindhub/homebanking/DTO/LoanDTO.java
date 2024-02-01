package com.mindhub.homebanking.DTO;

import com.mindhub.homebanking.models.Loans;
import jakarta.persistence.ElementCollection;

import java.util.List;

public class LoanDTO {
    private Long id;

    private String name;

    private double maxAmount, porcentage;

    private List<Integer> payments;

    private List<String> clientName;

    public LoanDTO(Loans loan){
        id = loan.getId();
        name = loan.getName();
        maxAmount = loan.getMaxAmount();
        payments = loan.getPayments();
        porcentage = loan.getPercentage();
        clientName = loan.getClientsLoans().stream().map(clientsLoans -> clientsLoans.getClient().getFirstName()).toList();
    }

    public double getPorcentage() {
        return porcentage;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getMaxAmount() {
        return maxAmount;
    }

    public List<Integer> getPayments() {
        return payments;
    }
}
