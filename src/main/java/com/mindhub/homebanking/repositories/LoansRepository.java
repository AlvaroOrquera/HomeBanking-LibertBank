package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.Loans;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface LoansRepository extends JpaRepository<Loans, Long> {
}
