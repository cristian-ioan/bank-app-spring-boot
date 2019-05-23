package com.banking.util;

import java.nio.charset.Charset;
import java.util.Random;

public final class TokenGeneratorUtils {

    private TokenGeneratorUtils(){};

    public static final String generateToken(){
        byte[] array = new byte[20];
        new Random().nextBytes(array);
        String generatedToken = new String(array, Charset.forName("UTF-8"));
        return generatedToken;
    }
}
