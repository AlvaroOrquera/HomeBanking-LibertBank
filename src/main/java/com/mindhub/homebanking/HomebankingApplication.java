package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class HomebankingApplication {

    public static void main(String[] args) {
        SpringApplication.run(HomebankingApplication.class, args);
    }

    @Bean
    public CommandLineRunner initData(ClientRepository clientRepository,
                                      AccountRepository accountRepository,
                                      TransactionRepository transactionRepository) {
        return args -> {
            Client numero1 = new Client("Melba", "Morel", "melba@mindhub.com");
            Client numero2 = new Client("Alvaro", "Orquera", "alvarosop23@gmail.com");
            System.out.println(numero1);
            System.out.println(numero2);
            clientRepository.save(numero1);
            clientRepository.save(numero2);
            Account vin001 = new Account("VIN001", LocalDate.now(), 5000);
            Account vin002 = new Account("VIN002", LocalDate.now().plusDays(1), 7500);
            Account vin003 = new Account("VIN003", LocalDate.now(), 5000);
            Account vin004 = new Account("VIN004", LocalDate.now().plusDays(1), 7500);
            numero1.addAccount(vin001);
            numero1.addAccount(vin002);

            numero2.addAccount(vin003);
            numero2.addAccount(vin004);
            accountRepository.save(vin001);
            accountRepository.save(vin002);

            accountRepository.save(vin003);
            accountRepository.save(vin004);
            Transaction trans1 = new Transaction(TransactionType.Credit, 20000, "Food", LocalDateTime.now());
            Transaction trans2 = new Transaction(TransactionType.Debit, 10000, "Clothing ", LocalDateTime.now());

            Transaction trans4 = new Transaction(TransactionType.Credit, 40000, "Food", LocalDateTime.now());
            Transaction trans3 = new Transaction(TransactionType.Debit, 60000, "Clothing ", LocalDateTime.now());


            Transaction trans5 = new Transaction(TransactionType.Credit, 20000, "Food", LocalDateTime.now());
            Transaction trans6 = new Transaction(TransactionType.Debit, 10000, "Clothing ", LocalDateTime.now());

            Transaction trans8 = new Transaction(TransactionType.Credit, 40000, "Food", LocalDateTime.now());
            Transaction trans7 = new Transaction(TransactionType.Debit, 60000, "Clothing ", LocalDateTime.now());

            vin001.addTransaction(trans1);
            vin002.addTransaction(trans2);

            vin001.addTransaction(trans3);
            vin002.addTransaction(trans4);

            vin003.addTransaction(trans5);
            vin004.addTransaction(trans6);

            vin003.addTransaction(trans7);
            vin004.addTransaction(trans8);
            transactionRepository.save(trans1);
            transactionRepository.save(trans2);
            transactionRepository.save(trans3);
            transactionRepository.save(trans4);
            transactionRepository.save(trans5);
            transactionRepository.save(trans6);
            transactionRepository.save(trans7);
            transactionRepository.save(trans8);

        };
    }


}
