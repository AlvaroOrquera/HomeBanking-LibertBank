package com.mindhub.homebanking.services.Implement;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardStatus;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardServiceImpl implements CardService {
    @Autowired
    CardRepository cardRepository;

    @Override
    public void cardSave(Card card) {
        cardRepository.save(card);
    }

    @Override
    public Card findById(Long id) {
        return cardRepository.findById(id).orElse(null);
    }
    @Override
    public void cancelarTarjeta(Card card) {
        Card cards = cardRepository.findById(card.getId()).orElse(null);
        cards.setStatus(CardStatus.INACTIVE);
        cardRepository.save(card);
    }
}
