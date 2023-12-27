package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.DTO.AccountDTO;
import com.mindhub.homebanking.DTO.TransactionDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.hibernate.bytecode.internal.bytebuddy.BytecodeProviderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ClientRepository clientRepository;


    @GetMapping("/accounts/all")
    public List<AccountDTO> getAllAccount() {
        return accountRepository.findAll()
                .stream()
                .map(account -> new AccountDTO(account))
                .collect(Collectors.toList());
    }

    @GetMapping("/accounts/{id}/transactions")
    public List<TransactionDTO> getOneAccount(@PathVariable Long id) {
        return accountRepository.findById(id)
                .map(account -> account.getTransactions().stream()
                        .map(TransactionDTO -> new TransactionDTO(TransactionDTO))
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }

    @PostMapping("/clients/current/accounts")
    public ResponseEntity<String> newAccount(Authentication authentication) {

        Client client = clientRepository.findByEmail(authentication.getName());
        if (client.getAccounts().size() >= 3) {
            return ResponseEntity.badRequest().body("you can't have more 3 accounts");
        }
        String number;
        do {
            number = "VIN-" + getRandomNumber(10000000, 99999999);
        } while (accountRepository.existsByNumber(number));
        Account account= new Account(number,LocalDate.now(),0.0);
        clientRepository.save(client);
        client.addAccount(account);
        accountRepository.save(account);
        return new ResponseEntity<>("Your account is created", HttpStatus.CREATED);
    }

    //con este metodo me creo un numero random para la cuenta
    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

}
