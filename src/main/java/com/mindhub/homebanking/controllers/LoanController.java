package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.DTO.LoanAplicationDTO;
import com.mindhub.homebanking.DTO.LoanDTO;
import com.mindhub.homebanking.DTO.NewLoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.services.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("/api")
public class LoanController {
    @Autowired
    AccountService accountService;
    @Autowired
    ClientService clientService;
    @Autowired
    LoanService loanService;
    @Autowired
    TransactionService transactionService;
    @Autowired
    ClientLoansService clientLoansService;

    @GetMapping("/loans")
    public List<LoanDTO> getAllLoan() {
        return loanService.getAllLoans();
    }

    @Transactional
    @PostMapping("/loans")
    public ResponseEntity<Object> createLoans(Authentication authentication,
                                              @RequestBody LoanAplicationDTO loanAplicationDTO) {

        Client client = clientService.getAuthenticatedClient(authentication.getName());
        Loans loans = loanService.findById(loanAplicationDTO.getId());
        Account destinationAccountNumber = accountService.findByNumber(loanAplicationDTO.getDestinationAccount());

        if (loanAplicationDTO.getAmount() <= 0 || loanAplicationDTO.getDestinationAccount() == null) {
            return new ResponseEntity<>("the data is incorrect", HttpStatus.FORBIDDEN);
        }
        if (destinationAccountNumber == null) {
            return new ResponseEntity<>("This account don't exists.", HttpStatus.FORBIDDEN);
        }
        if (client.getAccounts().equals(destinationAccountNumber)) {
            return new ResponseEntity<>("This isn't your account.", HttpStatus.FORBIDDEN);
        }
        if (loans == null) {
            return new ResponseEntity<>("This loan not exists.", HttpStatus.FORBIDDEN);
        }
        if (loanAplicationDTO.getAmount() >= loanService.findById(loanAplicationDTO.getId()).getMaxAmount()) {
            return new ResponseEntity<>("Your loans isn't accepted", HttpStatus.FORBIDDEN);
        }
        if (!loans.getPayments().contains(loanAplicationDTO.getPayments())) {
            return new ResponseEntity<>("these payments don;t exists.", HttpStatus.FORBIDDEN);
        }

        double amountPorcentage = loanAplicationDTO.getAmount() * (1 + (loans.getPercentage() / 100));
        ClientsLoans clientsLoans = new ClientsLoans(amountPorcentage, loanAplicationDTO.getPayments());
        client.addClientsLoans(clientsLoans);
        loans.addtClientsLoans(clientsLoans);
        clientLoansService.saveClientsLoans(clientsLoans);

        Transaction creditTrans = new Transaction(TransactionType.Credit, loanAplicationDTO.getAmount(), loans.getName() + " Loan Approved", LocalDateTime.now());
        destinationAccountNumber.addTransaction(creditTrans);
        transactionService.saveTransactions(creditTrans);

        destinationAccountNumber.setBalance(destinationAccountNumber.getBalance() + loanAplicationDTO.getAmount());
        accountService.saveAccount(destinationAccountNumber);

        return new ResponseEntity<>("Your loan is aprobaded.", HttpStatus.CREATED);
    }

    @Transactional
    @PostMapping("/admin/loans")
    public ResponseEntity<Object> createLoan(Authentication authentication,
                                             @RequestBody NewLoanDTO newLoanDTO) {
        Client client = clientService.getAuthenticatedClient(authentication.getName());
        List<Integer> payments = newLoanDTO.getPayments();
            if (newLoanDTO.getName().isBlank()) {
                return ResponseEntity.badRequest().body("The name cannot blank");
            }
            if (newLoanDTO.getPercentage() == 0) {
                return ResponseEntity.badRequest().body("The percentage cannot be 0.");
            }
            if (newLoanDTO.getMaxAmount() <= 0) {
                return ResponseEntity.badRequest().body("The maxAmount cannot less 0");
            }
            if (payments.size() == 0) {
                return ResponseEntity.badRequest().body("Invalid number of payments. Please provide a valid list of payments.");
            }
            Loans newLoan = new Loans(newLoanDTO.getName(), newLoanDTO.getMaxAmount(), newLoanDTO.getPayments(), newLoanDTO.getPercentage());
            loanService.saveLoan(newLoan);
        return ResponseEntity.accepted().body("The loan was successfully created.");
    }
}
