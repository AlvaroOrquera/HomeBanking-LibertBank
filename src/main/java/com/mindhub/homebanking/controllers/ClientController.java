package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.DTO.ClientDTO;
import com.mindhub.homebanking.DTO.CreateClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

//esto tambien es un servlet es el conjunto de las dos cosas, donde podemos decir que
// es un programa que  escucha y responde(en JSON/XML) las peticiones(HTTP metodos(GET PUT POST DELETE))
@RestController
//este indica la ruta donde va a estar escuchando el controlador
@RequestMapping("/api")// este sirve para devolver lo que le pidas basado en una ruta de acceso
public class ClientController {


    @Autowired //es algo similar a generar una instancia de esto, osea evita hacer el constructor
    //osea hacemos una inyeccion de dependencias
    private ClientRepository clientRepository;
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ClientService  clientService;
    @Autowired
    private AccountService accountService;

    //esto es un servlet= microprograma que responde peticiones especificas
    //RequestMapping por defecto es tipo GET
    @GetMapping("/all")
    public List<ClientDTO> getClients() {
        return clientService.getAllClientDTO();
    }

    @GetMapping("/clients/current")
    public ResponseEntity<ClientDTO> getOneClient(Authentication authentication) {

        // Buscar al cliente en la base de datos utilizando el nombre de usuario (email) al findByEmail lo sacamos
        // de clientRepository
        Client client = clientService.getAuthenticatedClient(authentication.getName());

        // Devolver una respuesta HTTP con el objeto ClientDTO y el código de estado OK (200)
        return new ResponseEntity<>(new ClientDTO(client), HttpStatus.OK);
    }

    @Autowired
    public PasswordEncoder passwordEncoder;
    //requestparams: es una anotación en Spring Framework que se utiliza para
    // extraer y vincular valores de parámetros de una solicitud HTTP a los parámetros de un método controlador.




    @PostMapping("/clients")
    public ResponseEntity<String> createClient(@RequestBody CreateClientDTO createClientDTO) {
        if (createClientDTO.getFirstName().isBlank()) {
            return new ResponseEntity<>("The name cannot be blank", HttpStatus.FORBIDDEN); //forbidden es el 403
        }
        if (createClientDTO.getLastName().isBlank()) {
            return new ResponseEntity<>("The lastname cannot be blank", HttpStatus.FORBIDDEN); //forbidden es el 403
        }
        if (createClientDTO.getEmail().isBlank()) {
            return new ResponseEntity<>("The email cannot be blank", HttpStatus.FORBIDDEN); //forbidden es el 403
        }
        if (createClientDTO.getPassword().isBlank()) {
            return new ResponseEntity<>("The password cannot be blank", HttpStatus.FORBIDDEN); //forbidden es el 403
        }
        if (clientService.existsByEmail(createClientDTO.getEmail())) {
            return new ResponseEntity<>("this email is used", HttpStatus.FORBIDDEN);
        }

        Client client = new Client(createClientDTO.getFirstName(),createClientDTO.getLastName(), createClientDTO.getEmail(), passwordEncoder.encode(createClientDTO.getPassword()));
        clientService.saveClient(client);

        String number;
        do {
            number = "VIN-" + getRandomNumber(10000000, 99999999);
        } while (accountService.existsByNumber(number));
        Account account = new Account(number, LocalDate.now(), 0.0);
        client.addAccount(account);
        accountService.saveAccount(account);

        return new ResponseEntity<>("successfully registered", HttpStatus.CREATED);
    }





    //con este metodo me creo un numero random para la cuenta
    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }


}
