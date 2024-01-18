package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Loans;
import com.mindhub.homebanking.repositories.LoansRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class LoanRepositoryTest {

    @Autowired
    LoansRepository loansRepository;

    //verificacion de que exista loans
    @Test
    public void existLoans() {
        List<Loans> loans = loansRepository.findAll();
        assertThat(loans, hasItems());
    }

    //verificacion de un loans llamdo mortgage.
    @Test
    public void existLoanMortgage() {
        List<Loans> loans = loansRepository.findAll();
        assertThat(loans, hasItems(hasProperty("name", is("Mortgage"))));
    }


}
