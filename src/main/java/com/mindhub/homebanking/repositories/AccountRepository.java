package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource //sirve para
public interface AccountRepository extends JpaRepository<Account, Long> {
    //le pongo string para marcarla el tipo de dato que es el number y coincida con el de account
    Account findByNumber(String number);
    boolean existsByNumber(String number);
}
