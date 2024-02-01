//package com.mindhub.homebanking;
//
//import com.mindhub.homebanking.models.*;
//import com.mindhub.homebanking.repositories.ClientRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//
//import java.util.List;
//
//import static org.hamcrest.MatcherAssert.assertThat;
//
//import static org.hamcrest.Matchers.*;
//import static org.junit.jupiter.api.Assertions.*;
//
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//public class ClientRepositoryTest {
//    @Autowired
//    ClientRepository clientRepository;
//
//    @Test
//    public void findClientById() {
//        Client client = clientRepository.findById(1L).orElse(null);
//        assertNotNull(client);
//        assertEquals("Melba", client.getFirstName());
//    }
//    @Test
//    public void allClientsHaveLastName() {
//        List<Client> clients = clientRepository.findAll();
//        assertThat(clients, hasItems(hasProperty("lastname",notNullValue())));
//    }
//    @Test
//    public void findAllClientsNotEmpty() {
//        List<Client> clients = clientRepository.findAll();
//        assertThat(clients, is(not(empty())));
//    }
//}
