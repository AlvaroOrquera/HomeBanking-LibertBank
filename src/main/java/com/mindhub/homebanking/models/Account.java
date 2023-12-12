package com.mindhub.homebanking.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String number;
    private LocalDate createDate;
    private double balance;
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    //creo la lista tipo set para que no me salgan repetidas, el Hashset: cumple una funcion similar a la de arraylist
    //construye un conjunto nuevo y vacio
    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER)
    private Set<Transaction> transactions = new HashSet<>() {

    };

    public Set<Transaction> getTransactions() {
        return transactions;
    }
     public void addTransaction(Transaction transaction){
         transaction.setAccount(this);
         transactions.add(transaction);
     }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }
//fin del set

    public Account() {
    }

    public Account(String number, LocalDate createDate, double balance) {
        this.number = number;
        this.createDate = createDate;
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }


    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
