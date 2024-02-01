package com.mindhub.homebanking.models;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación muchos a uno con la entidad Client
    @ManyToOne
    private Client client;

    private String cardHolder;

    // Enumerado para el tipo de tarjeta
    @Enumerated(EnumType.STRING)
    private CardType type;

    // Enumerado para el color de la tarjeta
    @Enumerated(EnumType.STRING)
    private CardColors colors;

    private String number;

    // Enumerado para el estado de la tarjeta, valor por defecto: ACTIVA
    @Enumerated(EnumType.STRING)
    private CardStatus status = CardStatus.ACTIVE;

    private String cvv;

    // Fecha de emisión de la tarjeta
    private LocalDate fromDate;

    // Fecha de vencimiento de la tarjeta
    private LocalDate truDate;

    // Constructor por defecto
    public Card() {
    }

    // Constructor con parámetros
    public Card(String cardHolder, CardType type, CardColors colors, String number, CardStatus status, String cvv, LocalDate fromDate, LocalDate truDate) {
        this.type = type;
        this.colors = colors;
        this.number = number;
        this.cvv = cvv;
        this.fromDate = fromDate;
        this.truDate = truDate;
        this.cardHolder = cardHolder;
        this.status = status;
    }

    // Métodos de acceso y modificadores

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

    public CardStatus getStatus() {
        return status;
    }

    public void setStatus(CardStatus status) {
        this.status = status;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
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
