package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.DTO.ClientDTO;
import com.mindhub.homebanking.DTO.NewClientDTO;
import com.mindhub.homebanking.Utils.Utils;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.AccountType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


//esto tambien es un servlet es el conjunto de las dos cosas, donde podemos decir que
// es un programa que  escucha y responde(en JSON/XML) las peticiones(HTTP metodos(GET PUT POST DELETE))
@RestController
//este indica la ruta donde va a estar escuchando el controlador
@RequestMapping("/api")// este sirve para devolver lo que le pidas basado en una ruta de acceso
public class ClientController {


    @Autowired //es algo similar a generar una instancia de esto, osea evita hacer el constructor
    // osea hacemos una inyeccion de dependencias
    private ClientService clientService;
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
    public ResponseEntity<String> createClient(@RequestBody NewClientDTO createClientDTO) {
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

        Client client = new Client(createClientDTO.getFirstName(), createClientDTO.getLastName(), createClientDTO.getEmail(), passwordEncoder.encode(createClientDTO.getPassword()));
        clientService.saveClient(client);

        String number;
        do {
            number = Utils.generateAccountN();
        } while (accountService.existsByNumber(number));
        Account account = new Account(number, LocalDate.now(), 0.0, true, AccountType.CURRENT);
        client.addAccount(account);
        accountService.saveAccount(account);

        return new ResponseEntity<>("successfully registered", HttpStatus.CREATED);
    }
}
