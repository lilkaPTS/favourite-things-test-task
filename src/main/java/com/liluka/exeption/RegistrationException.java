package com.liluka.exeption;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class RegistrationException extends Exception {

    private HttpStatus httpStatus;

    public RegistrationException(String msg) {
        super(msg);
    }

    public RegistrationException(String msg, HttpStatus httpStatus) {
        super(msg);
        this.httpStatus = httpStatus;
    }
}
