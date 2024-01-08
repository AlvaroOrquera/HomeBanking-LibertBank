package com.mindhub.homebanking.services;
import com.mindhub.homebanking.DTO.LoanDTO;
import com.mindhub.homebanking.models.Loans;

import java.util.List;

public interface LoanService {
    List<LoanDTO> getAllLoans();
    List<Loans>getLoan();
    Loans findById(Long id);
    boolean existsById(Long id);


    void saveLoan(Loans loan);
}
