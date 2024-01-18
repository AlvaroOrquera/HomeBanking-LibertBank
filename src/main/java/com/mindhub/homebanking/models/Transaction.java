package com.mindhub.homebanking.models;

import jakarta.persistence.*;
import org.springframework.core.style.ToStringCreator;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    private double amount, previousBalance, currentBalance;
    private String description;
    private LocalDateTime dateTime;

    @ManyToOne
    private Account account;

    // Constructor vacío necesario para evitar errores en las peticiones
    public Transaction() {
    }

    // Constructor principal
    public Transaction(TransactionType type, double amount, String description, LocalDateTime dateTime) {
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.dateTime = dateTime;
    }

    // Métodos de acceso


    public Long getId() {
        return id;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getPreviousBalance() {
        return previousBalance;
    }

    public void setPreviousBalance(double previousBalance) {
        this.previousBalance = previousBalance;
    }

    public double getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(double currentBalance) {
        this.currentBalance = currentBalance;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    // Método toString para facilitar la visualización de objetos
    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", type=" + type +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", dateTime=" + dateTime +
                ", account=" + account +
                '}';
    }
}
