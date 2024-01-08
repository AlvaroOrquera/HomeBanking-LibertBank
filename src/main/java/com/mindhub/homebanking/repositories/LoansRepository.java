package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.DTO.LoanDTO;
import com.mindhub.homebanking.models.Loans;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource
public interface LoansRepository extends JpaRepository<Loans, Long> {
    boolean existsById(Long id);

}
