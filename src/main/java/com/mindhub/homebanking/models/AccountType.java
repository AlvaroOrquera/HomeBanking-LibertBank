package com.mindhub.homebanking.models;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public enum AccountType {
    @Enumerated(EnumType.STRING)
    CURRENT, SAVING
}