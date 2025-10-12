package com.facturia.security.exceptions;

public class BussinesException extends RuntimeException {

    public BussinesException(String message) {
        super(message);
    }

    public BussinesException(String message, Throwable cause) {
        super(message, cause);
    }

}
