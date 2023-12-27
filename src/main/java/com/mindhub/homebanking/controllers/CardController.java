package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColors;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
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
    ClientRepository clientRepository;
    @Autowired
    CardRepository cardRepository;

    @PostMapping("clients/current/cards")
    public ResponseEntity<String> createCards(@RequestParam CardColors colors,
                                              @RequestParam CardType type,
                                              Authentication authentication) {
        Client client = clientRepository.findByEmail(authentication.getName());
        //usamos long para indicar el tipo de dato que va a ser
        long colorType = client.getCards().stream()
                .filter(card -> card.getColors() == colors && card.getType() == type)
                .count();
        long color = client.getCards().stream().filter(card -> card.getColors() == colors).count();
        long types = client.getCards().stream().filter(card -> card.getType() == type).count();
        if(types >= 3){
            return new ResponseEntity<>("It cannot have more than 3 cards."+ type, HttpStatus.FORBIDDEN);
        }
        if (color >=2){
            return new ResponseEntity<>("It cannot have more cards of this color."+ color,HttpStatus.FORBIDDEN);
        }
        if (colorType >= 1){
            return new ResponseEntity<>("It cannot have more cards of this color"+ color + "and this type"+ type, HttpStatus.FORBIDDEN);
        }
        if (client.getCards().size()>=6){
            return new ResponseEntity<>(
                    "Exceeded the number of cards it can have; its limit is 6.", HttpStatus.FORBIDDEN);
        }
        Random random = new Random();
        int cvv = 100 + random.nextInt(900);  // Genera un número aleatorio entre 100 y 999
        String formattedCvv = String.format("%03d", cvv);  // Asegura que el CVV siempre tenga tres dígitos
        String cardNumber = generateRandomCardNumber();
        String cardHolder = client.getFirstName() + " " + client.getLastname();
        LocalDate fromDate= LocalDate.now();
        LocalDate truDate= fromDate.plusYears(5);
        Card card = new Card(cardHolder,type,colors, cardNumber,cvv,fromDate,truDate);
        client.addCard(card);
        cardRepository.save(card);
        return new ResponseEntity<>("The card was created.", HttpStatus.CREATED);


    }

    private String generateRandomCardNumber() {
        //implemento la clase random
        Random random = new Random();
        // Se utiliza StringBuilder para construir la cadena de números de tarjeta de forma eficiente.
        StringBuilder cardNumber = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            int section = 1000 + random.nextInt(9000); // Genera un número aleatorio entre 1000 y 9999
            cardNumber.append(section).append("-");
            //aca decimos que tenga un guion entre cada iteracion
        }
             //con este le quitamos el ultimo guin
        return cardNumber.substring(0, cardNumber.length() - 1);
    }
}
