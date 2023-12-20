package com.mindhub.homebanking.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity //crea una tabla en la base de datos
public class Client {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // hace que las columnas en la tabla de la base de datos

    private String firstName, lastName, email, password;
    //asigno y inicialiso el roltype
    private RoleType rol = RoleType.CLIENT;




    //set que conecta a client con clientsloans
    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private Set<ClientsLoans> clientsLoans = new HashSet<>();

    public Set<ClientsLoans> getClientsLoans() {
        return clientsLoans;
    }

    public void addClientsLoans(ClientsLoans clientsLoans) {
        clientsLoans.setClient(this);
        this.clientsLoans.add(clientsLoans);
    }


    public Client(String firstName, String lastname, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastname;
        this.email = email;
        this.password = password;
    }

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    //accounts es una propiedad y tiene que estar en private
    //eager lo que hace es traer todo lo relacionado con cliente
    //  onetomany decimos que un cliente va a tener varias cuentas
    // mappedby decimos que va a mapear las cuentas
    //fetch decimos como queremos que nos traiga los datos en relacion con el account
    private List<Account> accounts = new ArrayList<>();

    public List<Account> getAccounts() {
        return accounts;
    }

    public void addAccount(Account account) {
        account.setClient(this);
        this.accounts.add(account);
    }

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    public Set<Card> cards = new HashSet<>();

    public Set<Card> getCards() {
        return cards;
    }

    public void addCard(Card card) {
        card.setClient(this);
        this.cards.add(card);
    }

    //constructor vacio "client" que sirve cuando yo haga la peticion evite el error status=500
    public Client() {
    }

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


    public String getLastname() {
        return lastName;
    }

    public void setLastname(String lastname) {
        this.lastName = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Client{" + "id=" + id +
                ", name='" + firstName + '\'' +
                ", lastname='" + lastName + '\'' +
                ", email='" + email + '\'' + '}';
    }


}
