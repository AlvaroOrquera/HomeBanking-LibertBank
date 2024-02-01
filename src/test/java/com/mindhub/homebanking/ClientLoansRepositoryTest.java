//package com.mindhub.homebanking;
//import com.mindhub.homebanking.models.Client;
//import com.mindhub.homebanking.models.ClientsLoans;
//import com.mindhub.homebanking.models.Loans;
//import com.mindhub.homebanking.repositories.ClientsLoansRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//
//import static org.hamcrest.MatcherAssert.assertThat;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//public class ClientLoansRepositoryTest {
//    @Autowired
//    ClientsLoansRepository clientsLoansRepository;
//    // test para setear clientes
//    @Test
//    public void SetClient() {
//        ClientsLoans clientsLoans = new ClientsLoans();
//        Client client = new Client();
//        clientsLoans.setClient(client);
//
//        assertEquals(client, clientsLoans.getClient());
//    }
//// test de setear prestamos
//    @Test
//    public void SetLoans() {
//        ClientsLoans clientsLoans = new ClientsLoans();
//        Loans loans = new Loans();
//        clientsLoans.setLoans(loans);
//
//        assertEquals(loans, clientsLoans.getLoans());
//    }
////test del constructor
//    @Test
//    public void ClientsLoansConstructor() {
//        double amount = 10000.0;
//        Integer payments = 12;
//
//        ClientsLoans clientsLoans = new ClientsLoans(amount, payments);
//
//        assertEquals(amount, clientsLoans.getAmount());
//        assertEquals(payments, clientsLoans.getPayments());
//    }
//
//}
