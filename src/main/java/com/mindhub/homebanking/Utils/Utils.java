package com.mindhub.homebanking.Utils;

import java.util.Random;

public class Utils {
    public static String generateCvv() {
        Random random = new Random();
        Integer cvv = 100 + random.nextInt(900);  // Genera un número aleatorio entre 100 y 999
        String formattedCvv = String.format("%03d", cvv);  // Asegura que el CVV siempre tenga tres dígitos
        return formattedCvv.toString();
    }
    public static String generateCardN(){
        //implemento la clase random
        Random random = new Random();
        // Se utiliza StringBuilder para construir la cadena de números de tarjeta de forma eficiente.
        StringBuilder cardNumber = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            int section = 1000 + random.nextInt(9000); // Genera un número aleatorio entre 1000 y 9999
            cardNumber.append(section);
            //aca decimos que tenga un guion entre cada iteracion
            if (i < 3) {
                cardNumber.append("-");
            }
        }
        //con este le quitamos el ultimo guin
        return cardNumber.toString();
    }
    public static String generateAccountN(){
      String number=  "VIN-" + getRandomNumber(10000000, 99999999);
      return number;
    }
    // Método para generar un número aleatorio
    private static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

}
