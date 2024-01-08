package com.mindhub.homebanking.controllers;

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
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class AccountController {

    @Autowired
    private ClientService clientService;
    @Autowired
    private AccountService accountService;


    @GetMapping("/accounts/all")
    public List<AccountDTO> getAllAccount() {
        return accountService.getAllAccounts();
    }

    @GetMapping("/accounts/{id}/transactions")
    public List<TransactionDTO> getOneAccount(@PathVariable Long id) {
        return accountService.getTransactionsByAccountId(id);
    }

    @PostMapping("/clients/current/accounts")
    public ResponseEntity<String> newAccount(Authentication authentication) {
        Client client = clientService.getAuthenticatedClient(authentication.getName());

        if (client.getAccounts().size() >= 3) {
            return ResponseEntity.badRequest().body("You can't have more than 3 accounts");
        }

        String number;
        do {
            number = "VIN-" + getRandomNumber(10000000, 99999999);
        } while (accountService.existsByNumber(number));

        Account account = new Account(number, LocalDate.now(), 0.0);
        client.addAccount(account);
        accountService.saveAccount(account);

        return new ResponseEntity<>("Your account is created", HttpStatus.CREATED);
    }

    // Método para generar un número aleatorio
    private int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
