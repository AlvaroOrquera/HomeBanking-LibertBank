package com.mindhub.homebanking.services;

import com.mindhub.homebanking.DTO.AccountDTO;
import com.mindhub.homebanking.DTO.TransactionDTO;
import com.mindhub.homebanking.models.Account;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface AccountService {
    List<AccountDTO> getAllAccounts();
    List<TransactionDTO> getTransactionsByAccountId(Long accountId);
    boolean existsByNumber(String number);
    void saveAccount(Account account);
    Account findByNumber(String number);
}
