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
    private double percentage;

    // Colección de pagos asociada a este préstamo
    @ElementCollection
    private List<Integer> payments;

    // Relación uno a muchos con la entidad ClientsLoans
    @OneToMany(mappedBy = "loans", fetch = FetchType.EAGER)
    private Set<ClientsLoans> clientsLoans = new HashSet<>();

    // Constructor por defecto
    public Loans() {
    }

    // Constructor con parámetros
    public Loans(String name, double maxAmount, List<Integer> payments, double percentage) {
        this.name = name;
        this.maxAmount = maxAmount;
        this.payments = payments;
        this.percentage = percentage;
    }

    // Métodos de acceso y modificadores

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

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public List<Integer> getPayments() {
        return payments;
    }

    public void setPayments(List<Integer> payments) {
        this.payments = payments;
    }

    // Obtener la colección de préstamos de clientes
    public Set<ClientsLoans> getClientsLoans() {
        return clientsLoans;
    }

    // Añadir un préstamo de cliente a la colección
    public void addClientsLoans(ClientsLoans clientsLoans) {
        clientsLoans.setLoans(this);
        this.clientsLoans.add(clientsLoans);
    }

    // Representación en cadena para propósitos de impresión y depuración
    @Override
    public String toString() {
        return "Loans{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", maxAmount=" + maxAmount +
                ", percentage=" + percentage +
                ", payments=" + payments +
                ", clientsLoans=" + clientsLoans +
                '}';
    }
}
