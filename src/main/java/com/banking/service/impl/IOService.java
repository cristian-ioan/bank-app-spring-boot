package com.banking.service.impl;

import java.math.BigDecimal;
import java.util.Scanner;

public class IOService {

    private static IOService instance;
    private IOService(){}

    public static synchronized IOService getInstance(){
        if (instance == null){
            instance = new IOService();
        }
        return instance;
    }

    /**
     * Reads an integer from console.
     *
     * @return the value (int) read from console
     */
    public int readInteger() {
        return new Scanner(System.in).nextInt();
    }

    /**
     * Reads an big decimal from console.
     * @return the value (big decimal) read from console
     */
    public BigDecimal readBigDecimal() {
        return new Scanner(System.in).nextBigDecimal();
    }

    /**
     * Reads a string from console.
     *
     * @return the String read from console
     */
    public String readLine() {
        return new Scanner(System.in).next();
    }
}