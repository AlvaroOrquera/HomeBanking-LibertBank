package com.mindhub.homebanking.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName, lastName, email, password;

    @Enumerated(EnumType.STRING)
    private RoleType rol = RoleType.CLIENT;

    // Relación con ClientsLoans
    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private Set<ClientsLoans> clientsLoans = new HashSet<>();

    // Relación con Account
    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private List<Account> accounts = new ArrayList<>();

    // Relación con Card
    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private Set<Card> cards = new HashSet<>();

    // Constructor vacío necesario para evitar errores en las peticiones
    public Client() {
    }

    // Constructor principal
    public Client(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    // Métodos de acceso para ClientsLoans
    public Set<ClientsLoans> getClientsLoans() {
        return clientsLoans;
    }

    public void addClientsLoans(ClientsLoans clientsLoans) {
        clientsLoans.setClient(this);
        this.clientsLoans.add(clientsLoans);
    }

    // Métodos de acceso para Account
    public List<Account> getAccounts() {
        return accounts;
    }

    public void addAccount(Account account) {
        account.setClient(this);
        this.accounts.add(account);
    }

    // Métodos de acceso para Card
    public Set<Card> getCards() {
        return cards;
    }

    public void addCard(Card card) {
        card.setClient(this);
        this.cards.add(card);
    }

    // Métodos de acceso y modificación para otros atributos
    public RoleType getRol() {
        return rol;
    }

    public void setRol(RoleType rol) {
        this.rol = rol;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Método toString para facilitar la visualización de objetos
    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}

