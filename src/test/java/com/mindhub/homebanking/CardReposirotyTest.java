//package com.mindhub.homebanking;
//
//import com.mindhub.homebanking.models.Card;
//import com.mindhub.homebanking.repositories.CardRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//
//import java.util.List;
//
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.*;
//import static org.junit.jupiter.api.Assertions.*;
//
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//public class CardReposirotyTest {
//    @Autowired
//    CardRepository cardRepository;
//
//    @Test
//    public void allCardsHaveNumber() {
//        List<Card> cards = cardRepository.findAll();
//        assertThat(cards, everyItem(hasProperty("number", notNullValue())));
//    }
//
//    @Test
//    public void findAllCardsNotEmpty() {
//        List<Card> cards = cardRepository.findAll();
//        assertThat(cards, is(not(empty())));
//    }
//
//    @Test
//    public void findCardByHolderName() {
//        List<Card> card = cardRepository.findByCardHolder("Mebal Morel");
//        assertNotNull(card);
//    }
//
//
//}
