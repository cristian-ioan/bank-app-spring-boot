package com.banking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Token not found!")
public class WrongTokenException extends Exception {
    public WrongTokenException (String errorMessage){
        super(errorMessage);
    }
}
