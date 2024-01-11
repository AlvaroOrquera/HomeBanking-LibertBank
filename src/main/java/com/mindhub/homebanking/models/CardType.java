package com.mindhub.homebanking.models;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public enum CardType {
   @Enumerated(EnumType.STRING)
   DEBIT, CREDIT
}
