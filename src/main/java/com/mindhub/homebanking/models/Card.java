package com.mindhub.homebanking.models;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.time.LocalDate;

@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Client client;
    private String cardHolder;
    private CardType type;
    private CardColors colors;
    private String number;
    private Integer cvv;
    private LocalDate fromDate;
    private LocalDate truDate;

    public Card() {
    }

    public Card(String cardHolder, CardType type, CardColors colors, String number, Integer cvv, LocalDate fromDate, LocalDate truDate) {
        this.type = type;
        this.colors = colors;
        this.number = number;
        this.cvv = cvv;
        this.fromDate = fromDate;
        this.truDate = truDate;
        this.cardHolder = cardHolder;
    }

    public Long getId() {
        return id;
    }


    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public CardType getType() {
        return type;
    }

    public void setType(CardType type) {
        this.type = type;
    }

    public CardColors getColors() {
        return colors;
    }

    public void setColors(CardColors colors) {
        this.colors = colors;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Integer getCvv() {
        return cvv;
    }

    public void setCvv(Integer cvv) {
        this.cvv = cvv;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getTruDate() {
        return truDate;
    }

    public void setTruDate(LocalDate truDate) {
        this.truDate = truDate;
    }
}
