package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Transactional
@RestController
@RequestMapping("/api")
public class TransactionsController {
    @Autowired
    AccountService accountService;
    @Autowired
    TransactionService transactionService;


    @PostMapping("/transactions")
    public ResponseEntity<String> createTransactions(@RequestParam double amount,
                                                     @RequestParam String originAccount,
                                                     @RequestParam String destinationAccount,
                                                     @RequestParam String description,
                                                     Authentication authentication) {

        Account originAccountNumber = accountService.findByNumber(originAccount);
        Account destinationAccountNumber = accountService.findByNumber(destinationAccount);
        if (amount <= 0) {
            return new ResponseEntity<>("The amount can't be 0", HttpStatus.FORBIDDEN);
        }
        if (description.isBlank()) {
            return new ResponseEntity<>("The description can't be in blank", HttpStatus.FORBIDDEN);
        }
        if (originAccountNumber == null) {
            return new ResponseEntity<>("This account doesn't exist.", HttpStatus.FORBIDDEN);
        }
        if (destinationAccountNumber == null) {
            return new ResponseEntity<>("This account doesn't exist.", HttpStatus.FORBIDDEN);
        }
        if (!originAccountNumber.getClient().getEmail().equals(authentication.getName())) {
            return new ResponseEntity<>("Origin account doesn't belong to a client.", HttpStatus.FORBIDDEN);
        }
        if (originAccountNumber.getBalance() < amount) {
            return new ResponseEntity<>("This account haven't suficcient funds", HttpStatus.FORBIDDEN);
        }
        if (originAccountNumber.equals(destinationAccountNumber)) {
            return new ResponseEntity<>("Origin Account and Destination Account can't be the same", HttpStatus.FORBIDDEN);
        }

        Transaction debitTrans = new Transaction(TransactionType.Debit, amount, description, LocalDateTime.now());
        Transaction creditTrans = new Transaction(TransactionType.Credit, amount, description, LocalDateTime.now());

        originAccountNumber.addTransaction(debitTrans);
        destinationAccountNumber.addTransaction(creditTrans);

        transactionService.saveTransactions(debitTrans);
        transactionService.saveTransactions(creditTrans);

        destinationAccountNumber.setBalance(destinationAccountNumber.getBalance() + amount);
        originAccountNumber.setBalance(originAccountNumber.getBalance() - amount);

        accountService.saveAccount(originAccountNumber);
        accountService.saveAccount(destinationAccountNumber);

        return new ResponseEntity<>("Succesful transaction.", HttpStatus.CREATED);
    }

}
