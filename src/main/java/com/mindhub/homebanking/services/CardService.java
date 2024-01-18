package com.mindhub.homebanking.services;

import com.mindhub.homebanking.models.Card;

public interface CardService {
    void cardSave(Card card);
    Card findById(Long id);
    void cancelarTarjeta(Card card);
}
