package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColors;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.CardRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CardReposirotyTest {
    @Autowired
    CardRepository cardRepository;
    //Verifica que el método setClient en la
    // clase Card establece correctamente el cliente asociado a la tarjeta.
    @Test
    public void SetClient() {
        Card card = new Card();
        Client client = new Client();
        card.setClient(client);

        assertEquals(client, card.getClient());
    }
    //Verifica que el constructor de Card que toma enums como argumentos
    // inicializa correctamente los atributos correspondientes de la tarjeta.
    @Test
    public void CardConstructorWithEnums() {
        String cardHolder = "Alva El Listo";
        CardType type = CardType.CREDIT;
        CardColors colors = CardColors.TITANIUM;

        Card card = new Card(cardHolder, type, colors, "9876543210987654", 456, LocalDate.now(), LocalDate.now().plusYears(2));

        assertEquals(cardHolder, card.getCardHolder());
        assertEquals(type, card.getType());
        assertEquals(colors, card.getColors());
    }
}
