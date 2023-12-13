package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.ClientsLoans;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ClientsLoansRepository extends JpaRepository<ClientsLoans, Long> {
}
