package com.facturia.security.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class DataBaseException extends RuntimeException {

    private final HttpStatus httpStatus;
    private final String type;
    private final String code;

    public DataBaseException(HttpStatus httpStatus,
                             String type, String code, String message) {
        super(message);
        this.httpStatus = httpStatus;
        this.type = type;
        this.code = code;
    }

    public DataBaseException(Throwable cause, HttpStatus httpStatus,
                             String type, String code, String message) {
        super(message);
        this.httpStatus = httpStatus;
        this.type = type;
        this.code = code;
    }

}
