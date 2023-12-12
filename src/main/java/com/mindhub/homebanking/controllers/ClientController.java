    package com.mindhub.homebanking.controllers;

    import com.mindhub.homebanking.DTO.ClientDTO;
    import com.mindhub.homebanking.repositories.ClientRepository;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.web.bind.annotation.PathVariable;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RestController;

    import java.util.List;
    import java.util.stream.Collectors;

    //esto tambien es un servlet es el conjunto de las dos cosas, donde podemos decir que
    // es un programa que controla y escucha las peticiones
    @RestController
    //este indica la ruta donde va a estar escuchando el controlador
    @RequestMapping("/api/clients")// este sirve para devolver lo que le pidas basado en una ruta de acceso
    public class ClientController {
        @Autowired //es algo similar a generar una instancia de esto, osea envita hacer el constructor
        private ClientRepository clientRepository;


        //esto es un servlet= microprograma que responde peticiones especificas
        @RequestMapping("/all")
        public List<ClientDTO> getAllClient(){
            return clientRepository.findAll()
                    .stream()
                    .map(client -> new ClientDTO(client))
                    .collect(Collectors.toList());
        };
        @RequestMapping("/{id}")
        public ClientDTO getOneClient(@PathVariable Long id){
            return new ClientDTO(clientRepository.findById(id).orElse(null));
        }
        // pathvariable sirve para variar la ruta del id, que puede ser cualquiera,
        //  y con el orElse decimos que si no encuentra un id, devuelva null
    }
