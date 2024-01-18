package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.DTO.NewTransactionsDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
public class TransactionsController {
    @Autowired
    AccountService accountService;
    @Autowired
    TransactionService transactionService;

    @Transactional
    @PostMapping("/transactions")
    public ResponseEntity<String> createTransactions(@RequestBody NewTransactionsDTO createTransactionsDTO,
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

        Transaction debitTrans = new Transaction(TransactionType.Debit, -createTransactionsDTO.getAmount(), createTransactionsDTO.getDescription(), LocalDateTime.now());
        Transaction creditTrans = new Transaction(TransactionType.Credit, createTransactionsDTO.getAmount(), createTransactionsDTO.getDescription(), LocalDateTime.now());

        double destinationAccountpreviousBalance = destinationAccountNumber.getBalance();
        double destinationAccountCurrentBalance = destinationAccountpreviousBalance + createTransactionsDTO.getAmount();
        double originAccountPreviousBalance = originAccountNumber.getBalance();
        double originAccountCurrentBalance =  originAccountPreviousBalance - createTransactionsDTO.getAmount();

        creditTrans.setPreviousBalance(destinationAccountpreviousBalance);
        creditTrans.setCurrentBalance(destinationAccountCurrentBalance);
        debitTrans.setPreviousBalance( originAccountPreviousBalance);
        debitTrans.setCurrentBalance(originAccountCurrentBalance);

        originAccountNumber.addTransaction(debitTrans);
        destinationAccountNumber.addTransaction(creditTrans);

        transactionService.saveTransactions(debitTrans);
        transactionService.saveTransactions(creditTrans);

        destinationAccountNumber.setBalance(destinationAccountCurrentBalance);
        originAccountNumber.setBalance(originAccountCurrentBalance);

        accountService.saveAccount(originAccountNumber);
        accountService.saveAccount(destinationAccountNumber);

        return new ResponseEntity<>("Succesful transaction.", HttpStatus.CREATED);
    }
}
