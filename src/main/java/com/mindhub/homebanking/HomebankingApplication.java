package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class HomebankingApplication {

    public static void main(String[] args) {
        SpringApplication.run(HomebankingApplication.class, args);
    }

    @Bean
    public CommandLineRunner initData(ClientRepository clientRepository,
                                      AccountRepository accountRepository) {
        return args -> {
            Client numero1 = new Client("Melba", "Morel", "melba@mindhub.com");
            Client numero2 = new Client("Alvaro", "Orquera", "alvarosop23@gmail.com");
            System.out.println(numero1);
            System.out.println(numero2);
            clientRepository.save(numero1);
            clientRepository.save(numero2);
            Account vin001 = new Account("VIN001", LocalDate.now(), 5000);
            Account vin002 = new Account("VIN002", LocalDate.now().plusDays(1), 7500);
            numero1.addAccount(vin001);
            numero1.addAccount(vin002);
            accountRepository.save(vin001);
            accountRepository.save(vin002);


        };
    }


}
