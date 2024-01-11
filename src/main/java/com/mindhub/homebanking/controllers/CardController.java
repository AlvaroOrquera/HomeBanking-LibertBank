package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.Utils.Utils;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColors;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.CardService;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Random;

@RestController
@RequestMapping("/api")
public class CardController {
    @Autowired
    ClientService clientService;
    @Autowired
    CardService cardService;

    @PostMapping("clients/current/cards")
    public ResponseEntity<String> createCards(@RequestParam CardColors colors,
                                              @RequestParam CardType type,
                                              Authentication authentication) {
        Client client = clientService.getAuthenticatedClient(authentication.getName());
        //usamos long para indicar el tipo de dato que va a ser
        long colorType = client.getCards().stream()
                .filter(card -> card.getColors() == colors && card.getType() == type)
                .count();
        long color = client.getCards().stream().filter(card -> card.getColors() == colors).count();
        long types = client.getCards().stream().filter(card -> card.getType() == type).count();
        if (types >= 3) {
            return new ResponseEntity<>("It cannot have more than 3 cards." + type, HttpStatus.FORBIDDEN);
        }
        if (color >= 2) {
            return new ResponseEntity<>("It cannot have more cards of this color." + color, HttpStatus.FORBIDDEN);
        }
        if (colorType >= 1) {
            return new ResponseEntity<>("It cannot have more cards of this color" + color + "and this type" + type, HttpStatus.FORBIDDEN);
        }
        if (client.getCards().size() >= 6) {
            return new ResponseEntity<>(
                    "Exceeded the number of cards it can have; its limit is 6.", HttpStatus.FORBIDDEN);
        }
        String cvv = Utils.generateCvv();
        String cardNumber = generateRandomCardNumber();
        String cardHolder = client.getFirstName() + " " + client.getLastname();
        LocalDate fromDate = LocalDate.now();
        LocalDate truDate = fromDate.plusYears(5);
        Card card = new Card(cardHolder, type, colors, cardNumber, cvv , fromDate, truDate);
        client.addCard(card);
        cardService.cardSave(card);
        return new ResponseEntity<>("The card was created.", HttpStatus.CREATED);


    }

    private String generateRandomCardNumber() {
       return Utils.generateCardN();
    }
}
