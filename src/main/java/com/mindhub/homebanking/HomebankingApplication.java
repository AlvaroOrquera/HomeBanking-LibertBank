package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class HomebankingApplication {

    public static void main(String[] args) {
        SpringApplication.run(HomebankingApplication.class, args);
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initData(ClientRepository clientRepository,
                                      AccountRepository accountRepository,
                                      TransactionRepository transactionRepository,
                                      ClientsLoansRepository clientsLoansRepository,
                                      LoansRepository loansRepository,
                                      CardRepository cardRepository) {


        return args -> {
            Client numero1 = new Client("Melba", "Morel", "melba@mindhub.com", passwordEncoder.encode("Melba1234"));
            Client numero2 = new Client("Alvaro", "Orquera", "alvarosop23@gmail.com", passwordEncoder.encode("Alvaro232315"));
            Client admin = new Client("Alvaro", "Orqueras", "alvarocapo2013@gmail.com", passwordEncoder.encode("Alvaro232315"));
            admin.setRol(RoleType.ADMIN);
            clientRepository.save(numero1);
            clientRepository.save(numero2);
            clientRepository.save(admin);
            System.out.println(numero1);
            System.out.println(numero2);
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
            Transaction trans2 = new Transaction(TransactionType.Debit, -10000, "Clothing ", LocalDateTime.now());

            Transaction trans4 = new Transaction(TransactionType.Credit, 40000, "Food", LocalDateTime.now());
            Transaction trans3 = new Transaction(TransactionType.Debit, -60000, "Clothing ", LocalDateTime.now());


            Transaction trans5 = new Transaction(TransactionType.Credit, 20000, "Food", LocalDateTime.now());
            Transaction trans6 = new Transaction(TransactionType.Debit, -10000, "Clothing ", LocalDateTime.now());

            Transaction trans8 = new Transaction(TransactionType.Credit, 40000, "Food", LocalDateTime.now());
            Transaction trans7 = new Transaction(TransactionType.Debit, -60000, "Clothing ", LocalDateTime.now());

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


            Loans Hipotecario = new Loans("Mortgage", 500000, List.of(12, 24, 36, 48, 60));
            Loans Personal = new Loans("Personal", 100000, List.of(6, 12, 24));
            Loans Automotriz = new Loans("Automotive", 300000, List.of(6, 12, 24, 36));

            loansRepository.save(Hipotecario);
            loansRepository.save(Personal);
            loansRepository.save(Automotriz);


            ClientsLoans melbaHipotecario = new ClientsLoans(400000, 60);
            ClientsLoans melbaPersonal = new ClientsLoans(50000, 12);
            ClientsLoans alvaroAutomotriz = new ClientsLoans(40000, 36);

            Hipotecario.addtClientsLoans(melbaHipotecario);
            Personal.addtClientsLoans(melbaPersonal);
            Automotriz.addtClientsLoans(alvaroAutomotriz);

            numero1.addClientsLoans(melbaHipotecario);
            numero1.addClientsLoans(melbaPersonal);
            numero2.addClientsLoans(alvaroAutomotriz);

            clientsLoansRepository.save(melbaHipotecario);
            clientsLoansRepository.save(melbaPersonal);
            clientsLoansRepository.save(alvaroAutomotriz);

            Card cardMelbaGold = new Card("Mebal Morel", CardType.DEBIT, CardColors.GOLD,
                    "3325-5745-7876-4445", 990, LocalDate.of(2021, 4, 26), LocalDate.of(2026, 4, 26));
            Card cardMelbaTitanium = new Card("Mebal Morel", CardType.CREDIT, CardColors.TITANIUM,
                    "2234-6745-5523-7888", 750, LocalDate.of(2021, 4, 26), LocalDate.of(2026, 4, 26));
            Card cardAlvaroSilver = new Card("Alvaro Orquera", CardType.CREDIT, CardColors.SILVER,
                    "2234-6745-7876-4445", 231, LocalDate.of(2021, 4, 26), LocalDate.of(2026, 4, 26));
            numero1.addCard(cardMelbaGold);
            numero1.addCard(cardMelbaTitanium);
            numero2.addCard(cardAlvaroSilver);
            cardRepository.save(cardAlvaroSilver);
            cardRepository.save(cardMelbaTitanium);
            cardRepository.save(cardMelbaGold);
            //el orden de guardado para un correcto funcionamiento seria: 1ro se guarda el cliente en el repositorio
            // 2do se guarda el loans que seria el uno para muchos que este va a la clase clientsLoans
            // y 3ro se guarda recien el clientsLoans que seria el mucho para uno


        };
    }


}
