package com.mindhub.homebanking.services.Implement;

import com.mindhub.homebanking.DTO.LoanDTO;
import com.mindhub.homebanking.models.Loans;
import com.mindhub.homebanking.repositories.LoansRepository;
import com.mindhub.homebanking.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoanServiceImpl implements LoanService {

    @Autowired
    LoansRepository loansRepository;

    @Override
    public List<LoanDTO> getAllLoans() {
        return getLoan().stream().map(loan -> new LoanDTO(loan)).collect(Collectors.toList());
    }

    @Override
    public List<Loans> getLoan() {
        return loansRepository.findAll();
    }

    @Override
    public Loans findById(Long id) {
        return loansRepository.findById(id).orElse(null);
    }

    @Override
    public boolean existsById(Long id) {
        return loansRepository.existsById(id);
    }


    @Override
    public void saveLoan(Loans loan) {
        loansRepository.save(loan);
    }
}
