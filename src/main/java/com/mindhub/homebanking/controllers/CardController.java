package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.Utils.Utils;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.services.CardService;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

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
            return new ResponseEntity<>("Exceeded the number of cards it can have; its limit is 6.", HttpStatus.FORBIDDEN);
        }


        CardStatus status = CardStatus.ACTIVE;
        String cvv = Utils.generateCvv();
        String cardNumber = generateRandomCardNumber();
        String cardHolder = client.getFirstName() + " " + client.getLastName();
        LocalDate fromDate = LocalDate.now();
        LocalDate truDate = fromDate.plusYears(5);
        Card card = new Card(cardHolder, type, colors, cardNumber, status, cvv, fromDate, truDate);
        client.addCard(card);
        cardService.cardSave(card);
        return new ResponseEntity<>("The card was created.", HttpStatus.CREATED);
    }
    private String generateRandomCardNumber() {
        return Utils.generateCardN();
    }

    @PatchMapping("clients/current/cards")
    public ResponseEntity<String> cancelarTarjeta(@RequestParam Long id, Authentication authentication) {
        Client client = clientService.getAuthenticatedClient(authentication.getName());
        Card cards = cardService.findById(id);

        if (cards.getStatus() == CardStatus.ACTIVE && cards.getClient().getEmail().equals(authentication.getName())) {
            cardService.cancelarTarjeta(cards);
            return ResponseEntity.ok("Ingenious card successfully canceled.");
        }
        return ResponseEntity.badRequest().body("Your card is canceled.");
    }
}
