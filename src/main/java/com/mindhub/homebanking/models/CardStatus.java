package com.mindhub.homebanking.models;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public enum EstatusCard {
    @Enumerated(EnumType.STRING)
    ACTIVA, INACTIVA, CANCELADA
}
