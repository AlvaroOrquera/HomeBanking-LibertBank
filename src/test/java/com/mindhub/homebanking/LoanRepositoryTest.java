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

    //aca esta bien que este test sea fallido ya que no existe un prestamo llamado JuanCarlos.
    @Test
    public void existLoansName() {
        Loans loans = new Loans("JuanCarlos", 30000, List.of(1, 2, 3, 4, 5));
        assertEquals(loans, hasItems(hasProperty("name", is("JuanCarlos"))));
    }
    //test de verificacion de constructor
    @Test
    public void LoansConstructor() {
        String name = "Loan A";
        double maxAmount = 10000.0;
        List<Integer> payments = new ArrayList<>();
        payments.add(1);
        payments.add(2);
        payments.add(3);

        Loans loans = new Loans(name, maxAmount, payments);

        assertEquals(name, loans.getName());
        assertEquals(maxAmount, loans.getMaxAmount());
        assertEquals(payments, loans.getPayments());
    }
}
