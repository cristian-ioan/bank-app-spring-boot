package com.banking.util;

import java.nio.charset.Charset;
import java.util.Random;

public final class TokenGeneratorUtils {

    private TokenGeneratorUtils(){};

    public static final String generateToken(){
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";
        StringBuilder generatedToken = new StringBuilder(20);
        for (int i = 0; i < 20; i++) {

            // generate a random number between 0 to AlphaNumericString variable length
            int index = (int)(AlphaNumericString.length() * Math.random());

            // add Character one by one in end of generatedToken
            generatedToken.append(AlphaNumericString.charAt(index));
        }
        return generatedToken.toString();
    }
}
