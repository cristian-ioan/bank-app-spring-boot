package com.banking.util;

import java.util.Random;

public final class IbanGeneratorUtils {

    private IbanGeneratorUtils(){};

    /**
     * Generates a new bank account(IBAN).
     *
     * @return iban  = the new bank account
     */
    public static final String generateIban() {

        Random value = new Random();
        String iban = "RO";
        String swiftCode = "RZBR";

        int r1 = value.nextInt( 10 );
        int r2 = value.nextInt( 10 );
        if (r1 == 0){
            if (r2 != 0){
                iban += "0" + r2 + swiftCode + "000000";
            } else {
                iban += "0" + "0" + swiftCode + "000000";
            }
        } else {
            if (r2 == 0){
                iban += r1 + "0" + swiftCode + "000000";
            } else {
                iban += r1 + r2 + swiftCode + "000000";
            }
        }

        int n;
        for (int i = 0; i < 10; i++) {
            n = value.nextInt( 10 );
            iban += Integer.toString(n) ;
        }
        return iban;
    }

}