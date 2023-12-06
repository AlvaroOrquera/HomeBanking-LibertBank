package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HomebankingApplication {

    public static void main(String[] args) {
        SpringApplication.run(HomebankingApplication.class, args);
    }

    @Bean
    public CommandLineRunner initData(ClientRepository clientRepository) {
        return args -> {
            Client numero1 = new Client("Alvaro", "Orquera", "alvarosop23@gmail.com");
            Client numero2 = new Client("Saul", "Palacios", "alvarocapo2013@gmail.com");
            System.out.println(numero1);
            System.out.println(numero2);
            clientRepository.save(numero1);
            clientRepository.save(numero2);

        };
    }


}
