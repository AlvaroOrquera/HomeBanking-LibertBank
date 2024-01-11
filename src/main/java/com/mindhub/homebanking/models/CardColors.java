package com.mindhub.homebanking.models;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public enum CardColors {
    @Enumerated(EnumType.STRING)
    GOLD, TITANIUM, SILVER
}
