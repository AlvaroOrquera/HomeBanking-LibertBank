package com.mindhub.homebanking.DTO;

import com.mindhub.homebanking.models.Loans;
import jakarta.persistence.ElementCollection;

import java.util.List;

public class LoanDTO {
    private Long id;

    private String name;

    private double maxAmount;

    private List<Integer> payments;

    public LoanDTO(Loans loan){
        id = loan.getId();
        name = loan.getName();
        maxAmount = loan.getMaxAmount();
        payments = loan.getPayments();

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
