package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;


@RepositoryRestResource
public interface ClientRepository extends JpaRepository<Client, Long> {
    //creamos una consulta en el repositorio para buscar el email
    //y para verificar que exista el email, esto se puede por la herencia de JpaRepository
    Client findByEmail(String email);
    boolean existsByEmail(String email);

}
