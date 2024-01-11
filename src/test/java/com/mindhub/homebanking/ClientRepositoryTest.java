package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ClientRepositoryTest {
    @Autowired
    ClientRepository clientRepository;

    @Test
    public void SearchClient(){
      List<Client> clients =clientRepository.findAll();
      assertThat(clients, hasItem(hasProperty("firstName", is("Melba"))));
    }
    //verificacion de password.
    @Test
    public void PasswordValidatorInvalid() {
        String invalidPassword = "invalid";
        assertThrows(IllegalAccessError.class, () -> Client.passwordValidator(invalidPassword));
    }
    //test de prueba para ver la asociacion entre clientloans,client y cards.
    @Test
    public void AssociationCardClientClientLoans() {
        Client client = new Client();

        ClientsLoans loan = new ClientsLoans();
        client.addClientsLoans(loan);
        assertTrue(client.getClientsLoans().contains(loan));

        Account account = new Account();
        client.addAccount(account);
        assertTrue(client.getAccounts().contains(account));

        Card card = new Card();
        client.addCard(card);
        assertTrue(client.getCards().contains(card));
    }

}
