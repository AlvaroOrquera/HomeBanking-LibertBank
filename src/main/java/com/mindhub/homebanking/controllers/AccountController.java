package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.DTO.AccountDTO;
import com.mindhub.homebanking.DTO.TransactionDTO;
import com.mindhub.homebanking.Utils.Utils;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.AccountType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.services.PdfService;
import com.mindhub.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.mindhub.homebanking.models.AccountType.CURRENT;

@RestController
@RequestMapping("/api")
public class AccountController {

    @Autowired
    private ClientService clientService;
    @Autowired
    private AccountService accountService;
    @Autowired
    TransactionService transactionService;
    @Autowired
    private PdfService pdfService;

    @GetMapping("/accounts/all")
    public List<AccountDTO> getAllAccount() {
        return accountService.getAllAccounts();
    }

    @GetMapping("/accounts/{id}/transactions")
    public List<TransactionDTO> getOneAccount(@PathVariable Long id) {
        return accountService.getTransactionsByAccountId(id);
    }

    @GetMapping("/accounts/{id}/transactions/pdf")
    public ResponseEntity<InputStreamResource> descargarPdf(@PathVariable Long id,
                                                            @RequestParam LocalDateTime dateTime,
                                                            @RequestParam LocalDateTime endTime,
                                                            Authentication authentication) throws IOException {
        Client client = clientService.getAuthenticatedClient(authentication.getName());
        if (client == null){
            return ResponseEntity.badRequest().body((new InputStreamResource(new ByteArrayInputStream("Your not the Owner".getBytes()))));
        }
        AccountDTO accountDTO = accountService.getAccountById(id);
        List<Transaction> transactions = transactionService.findByAccountIdAndDateTimeBetween(id, dateTime, endTime);
        ByteArrayInputStream pdf = pdfService.generatePdf(transactions,accountDTO);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=transactions.pdf");
        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(new InputStreamResource(pdf));
    }


    @PostMapping("/clients/current/accounts")
    public ResponseEntity<String> newAccount(@RequestParam String accountType, Authentication authentication) {
        Client client = clientService.getAuthenticatedClient(authentication.getName());
        AccountType type = AccountType.valueOf(accountType.toUpperCase());
        long currentAndSavingsCount = client.getAccounts().stream()
                .filter(account -> account.getAccountType() == type)
                .count();

        if (client.getAccounts().stream().filter(Account::isActive).collect(Collectors.toList()).size() >= 3) {
            if (type == AccountType.CURRENT && currentAndSavingsCount >= 3) {
                return ResponseEntity.badRequest().body("You already have 3 accounts of type Current");
            } else if (type == AccountType.SAVING && currentAndSavingsCount >= 3) {
                return ResponseEntity.badRequest().body("You already have 3 accounts of type Savings");
            }
        }

        String number;
        do {
            number = Utils.generateAccountN();
        } while (accountService.existsByNumber(number));

        Account account = new Account(number, LocalDate.now(), 0.0, true, type);
        client.addAccount(account);
        accountService.saveAccount(account);

        return new ResponseEntity<>("Your account is created", HttpStatus.CREATED);
    }

    @PatchMapping("/clients/current/accounts")
    public ResponseEntity<String> cancelarCuenta(@RequestParam String number, Authentication authentication) {
        Client client = clientService.getAuthenticatedClient(authentication.getName());
        Account account = accountService.findByNumber(number);
        if (account.isActive() && account.getClient().getEmail().equals(authentication.getName())) {
            if (account.getBalance() == 0) {
                accountService.cancelaCuenta(account);
                return ResponseEntity.ok("Ingenious Account successfully canceled.");
            } else {
                return ResponseEntity.badRequest().body("Your Account balance is not 0");
            }
        } else {
            return ResponseEntity.badRequest().body("This is not your account or the account is not active.");
        }
    }

}
