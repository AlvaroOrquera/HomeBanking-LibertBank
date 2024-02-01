package com.mindhub.homebanking.models;

import jakarta.persistence.*;

@Entity
public class ClientsLoans {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double amount;
    private Integer payments;

    @ManyToOne
    private Client client;

    @ManyToOne
    private Loans loans;



    // Constructores
    public ClientsLoans() {
    }

    public ClientsLoans(double amount, Integer payments) {
        this.amount = amount;
        this.payments = payments;
    }

    // Métodos de acceso
    public Long getId() {
        return id;
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

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Loans getLoans() {
        return loans;
    }

    public void setLoans(Loans loans) {
        this.loans = loans;
    }


    // Método toString
    @Override
    public String toString() {
        return "ClientsLoans{" +
                "id=" + id +
                ", amount=" + amount +
                ", payments=" + payments +
                ", client=" + client +
                ", loans=" + loans +
                '}';
    }
}
