package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.DTO.CreateTransactionsDTO;
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
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<String> createTransactions(@RequestBody CreateTransactionsDTO createTransactionsDTO,
                                                     Authentication authentication) {

        Account originAccountNumber = accountService.findByNumber(createTransactionsDTO.getOriginAccount());
        Account destinationAccountNumber = accountService.findByNumber(createTransactionsDTO.getDestinationAccount());
        if (createTransactionsDTO.getAmount() <= 0) {
            return new ResponseEntity<>("The amount can't be 0", HttpStatus.FORBIDDEN);
        }
        if (createTransactionsDTO.getDescription().isBlank()) {
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
        if (originAccountNumber.getBalance() < createTransactionsDTO.getAmount()) {
            return new ResponseEntity<>("This account haven't suficcient funds", HttpStatus.FORBIDDEN);
        }
        if (originAccountNumber.equals(destinationAccountNumber)) {
            return new ResponseEntity<>("Origin Account and Destination Account can't be the same", HttpStatus.FORBIDDEN);
        }

        Transaction debitTrans = new Transaction(TransactionType.Debit, createTransactionsDTO.getAmount(), createTransactionsDTO.getDescription(), LocalDateTime.now());
        Transaction creditTrans = new Transaction(TransactionType.Credit, createTransactionsDTO.getAmount(), createTransactionsDTO.getDescription(), LocalDateTime.now());

        originAccountNumber.addTransaction(debitTrans);
        destinationAccountNumber.addTransaction(creditTrans);

        transactionService.saveTransactions(debitTrans);
        transactionService.saveTransactions(creditTrans);

        destinationAccountNumber.setBalance(destinationAccountNumber.getBalance() + createTransactionsDTO.getAmount());
        originAccountNumber.setBalance(originAccountNumber.getBalance() - createTransactionsDTO.getAmount());

        accountService.saveAccount(originAccountNumber);
        accountService.saveAccount(destinationAccountNumber);

        return new ResponseEntity<>("Succesful transaction.", HttpStatus.CREATED);
    }

}
