package com.banking.exception;

public class WrongUserNamePasswordException extends Exception {

    /**
     * Creates a custom com.banking.exception for wrong username/password at login.
     */
    public WrongUserNamePasswordException(String errorMessage) {
        super(errorMessage);
    }

}
