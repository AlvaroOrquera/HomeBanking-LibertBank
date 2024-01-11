package com.mindhub.homebanking.models;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public enum RoleType {
    @Enumerated(EnumType.STRING)
    ADMIN, CLIENT
}
