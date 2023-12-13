package com.mindhub.homebanking.models;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Loans {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private double maxAmount;

   @ElementCollection
   private List<Integer> payments;

    //set para el clientloans
    @OneToMany(mappedBy = "loans", fetch = FetchType.EAGER)
    private Set<ClientsLoans> clientsLoans = new HashSet<>();

    public Set<ClientsLoans> getClientsLoans() {
        return clientsLoans;
    }

    public void addtClientsLoans(ClientsLoans clientsLoans) {
        clientsLoans.setLoans(this);
        this.clientsLoans.add(clientsLoans);
    }

    public Loans() {
    }


    public Loans(String name, double maxAmount, List<Integer> payments) {
        this.name = name;
        this.maxAmount = maxAmount;
        this.payments = payments;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(double maxAmount) {
        this.maxAmount = maxAmount;
    }

    public List<Integer> getPayments() {
        return payments;
    }

    public void setPayments(List<Integer> payments) {
        this.payments = payments;
    }

    @Override
    public String toString() {
        return "Loans{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", maxAmount=" + maxAmount +
                ", payments=" + payments +
                '}';
    }
}
