package com.mindhub.homebanking.DTO;

import com.mindhub.homebanking.models.Loans;
import jakarta.persistence.ElementCollection;

import java.util.List;

public class NewLoanDTO {
    private String name;
    private double maxAmount, percentage;
    @ElementCollection
    private List<Integer> payments;


    public String getName() {
        return name;
    }

    public double getMaxAmount() {
        return maxAmount;
    }

    public double getPercentage() {
        return percentage;
    }

    public List<Integer> getPayments() {
        return payments;
    }
}
