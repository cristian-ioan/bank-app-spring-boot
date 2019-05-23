package com.banking.exception;

public class BalanceException extends Exception {

    public BalanceException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
