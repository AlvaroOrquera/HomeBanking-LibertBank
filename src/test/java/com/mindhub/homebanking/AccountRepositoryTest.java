package com.mindhub.homebanking;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.repositories.AccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AccountRepositoryTest {
    @Autowired
    AccountRepository accountRepository;
    //test para verificar el constructor.
    @Test
    public void AccountConstructor() {
        String number = "123456789";
        LocalDate createDate = LocalDate.now();
        double balance = 1000.0;

        Account account = new Account(number, createDate, balance);

        assertEquals(number, account.getNumber());
        assertEquals(createDate, account.getCreateDate());
        assertEquals(balance, account.getBalance());
    }
    //verificacion para ver el comportamiento del metodo addTransaction
    @Test
    public void AddTransaction() {
        Account account = new Account();
        Transaction transaction = new Transaction();
        account.addTransaction(transaction);

        assertTrue(account.getTransactions().contains(transaction));
        assertEquals(account, transaction.getAccount());
    }
}
