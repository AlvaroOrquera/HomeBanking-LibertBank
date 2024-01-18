package com.mindhub.homebanking.services;

import com.mindhub.homebanking.models.Transaction;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionService {
    void saveTransactions(Transaction transaction);

    List<Transaction> findByAccountIdAndDateTimeBetween(Long id, LocalDateTime dateTime, LocalDateTime endTime);
}
