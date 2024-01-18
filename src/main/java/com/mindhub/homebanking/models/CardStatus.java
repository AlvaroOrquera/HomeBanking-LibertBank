package com.mindhub.homebanking.models;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public enum CardStatus {
    @Enumerated(EnumType.STRING)
    ACTIVE, INACTIVE
}
