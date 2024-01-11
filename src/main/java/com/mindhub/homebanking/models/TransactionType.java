package com.mindhub.homebanking.models;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public enum TransactionType {
    @Enumerated(EnumType.STRING)
    Debit,Credit

}
