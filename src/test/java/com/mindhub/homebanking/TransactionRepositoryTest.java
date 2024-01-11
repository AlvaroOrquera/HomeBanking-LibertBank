package com.mindhub.homebanking;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TransactionRepositoryTest {
    @Autowired
    TransactionRepository transactionRepository;

    //Verifica que el método setAccount conecte la transaccion a la cuenta asociada
    @Test
    public void SetAccount() {
        Transaction transaction = new Transaction();
        Account account = new Account();
        transaction.setAccount(account);

        assertEquals(account, transaction.getAccount());
    }
    // Verifica que el método toString no devuelva null
    @Test
    public void ToString() {
        Transaction transaction = new Transaction();
        assertNotNull(transaction.toString());
    }
}
