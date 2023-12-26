package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.DTO.ClientDTO;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

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


    //esto es un servlet= microprograma que responde peticiones especificas
    //RequestMapping por defecto es tipo GET
    @RequestMapping("/all")
    public List<ClientDTO> getAllClient() {
        return clientRepository.findAll()
                .stream()
                .map(client -> new ClientDTO(client))
                .collect(Collectors.toList());
    }

    ;
    @Autowired
    public PasswordEncoder passwordEncoder;
    //requestparams: es una anotación en Spring Framework que se utiliza para
    // extraer y vincular valores de parámetros de una solicitud HTTP a los parámetros de un método controlador.
    @PostMapping("/clients")
    public ResponseEntity<String> createClient(@RequestParam String firstName,
                                               @RequestParam String lastName,
                                               @RequestParam String email,
                                               @RequestParam String password) {
        if (firstName.isBlank()) {
            return new ResponseEntity<>("The name cannot be blank", HttpStatus.FORBIDDEN); //forbidden es el 403
        }
        if (lastName.isBlank()) {
            return new ResponseEntity<>("The lastname cannot be blank", HttpStatus.FORBIDDEN); //forbidden es el 403
        }
        if (email.isBlank()) {
            return new ResponseEntity<>("The email cannot be blank", HttpStatus.FORBIDDEN); //forbidden es el 403
        }
        if (password.isBlank()) {
            return new ResponseEntity<>("The password cannot be blank", HttpStatus.FORBIDDEN); //forbidden es el 403
        }
        if (clientRepository.existsByEmail(email)) {
            return new ResponseEntity<>("this email is used", HttpStatus.FORBIDDEN);

        }

        Client client = new Client(firstName, lastName, email, passwordEncoder.encode(password));
        clientRepository.save(client);
        return new ResponseEntity<>("successfully registered", HttpStatus.CREATED);
    }


    @RequestMapping("/clients/{id}")
    public ClientDTO getOneClient(@PathVariable Long id) {
        return new ClientDTO(clientRepository.findById(id).orElse(null));
    }
    // pathvariable sirve para variar la ruta del id, que puede ser cualquiera,
    //  y con el orElse decimos que si no encuentra un id, devuelva null

    @RequestMapping("/clients/current")
    public ResponseEntity<ClientDTO> getOneClient(Authentication authentication) {
        // Buscar al cliente en la base de datos utilizando el nombre de usuario (email) al findByEmail lo sacamos
        // de clientRepository
        Client client = clientRepository.findByEmail(authentication.getName());
        // Devolver una respuesta HTTP con el objeto ClientDTO y el código de estado OK (200)
        return new ResponseEntity<>(new ClientDTO(client), HttpStatus.OK);
    }



}
