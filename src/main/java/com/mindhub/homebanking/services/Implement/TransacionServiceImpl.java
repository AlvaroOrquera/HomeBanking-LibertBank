package com.mindhub.homebanking.services.Implement;

import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.repositories.TransactionRepository;
import com.mindhub.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransacionServiceImpl implements TransactionService {
    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public void saveTransactions(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    @Override
    public List<Transaction> findByAccountIdAndDateTimeBetween(Long id, LocalDateTime dateTime, LocalDateTime endTime) {
        return transactionRepository.findByAccountIdAndDateTimeBetween(id,dateTime,endTime);
    }


}
