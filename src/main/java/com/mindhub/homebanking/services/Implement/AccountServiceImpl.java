package com.mindhub.homebanking.services.Implement;

import com.mindhub.homebanking.DTO.AccountDTO;
import com.mindhub.homebanking.DTO.TransactionDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ClientService clientService;

    @Override
    public List<AccountDTO> getAllAccounts() {
        return accountRepository.findAll()
                .stream()
                .map(account -> new AccountDTO(account))
                .collect(Collectors.toList());
    }

    @Override
    public List<TransactionDTO> getTransactionsByAccountId(Long id) {
        return accountRepository.findById(id)
                .map(account -> account.getTransactions().stream()
                        .map(TransactionDTO -> new TransactionDTO(TransactionDTO))
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }

    @Override
    public boolean existsByNumber(String number) {
        return accountRepository.existsByNumber(number);
    }
    @Override
    public void saveAccount(Account account) {
        accountRepository.save(account);
    }

    @Override
    public Account findByNumber(String number) {
        return accountRepository.findByNumber(number);
    }


}

