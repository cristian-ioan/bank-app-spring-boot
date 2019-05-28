package com.banking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "User not found!")
public class WrongUserNamePasswordException extends Exception {

    /**
     * Creates a custom com.banking.exception for wrong username/password at login.
     */
    public WrongUserNamePasswordException(String errorMessage) {
        super(errorMessage);
    }

}
