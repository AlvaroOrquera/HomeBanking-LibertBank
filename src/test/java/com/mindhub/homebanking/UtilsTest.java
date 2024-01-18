package com.mindhub.homebanking;

import com.mindhub.homebanking.Utils.Utils;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UtilsTest {
    @Test
    public void testGenerateCvv() {
        String cvv = Utils.generateCvv();
        assertTrue(cvv.matches("\\d{3}"), "CVV debe tener tres dígitos");
    }
    @Test
    public void testGenerateCardNumber() {
        String cardNumber = Utils.generateCardN();
        assertTrue(cardNumber.matches("\\d{4}-\\d{4}-\\d{4}-\\d{4}"), "Número de tarjeta debe tener el formato correcto");
        assertTrue(cardNumber.replace("-", "").matches("\\d{16}"), "Número de tarjeta debe tener 16 dígitos sin guiones");
    }
    @Test
    public void cardNumberIsCreated(){
        String cardNumber = Utils.generateCardN();
        assertThat(cardNumber,is(not(emptyOrNullString())));
    }
}

