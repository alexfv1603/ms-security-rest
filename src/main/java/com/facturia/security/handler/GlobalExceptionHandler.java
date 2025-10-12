package com.facturia.security.handler;

import com.facturia.security.commons.Constants;
import com.facturia.security.exceptions.BussinesException;
import com.facturia.security.exceptions.DataBaseException;
import com.facturia.security.exceptions.InvalidHeaderException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        ErrorResponse errorResponse = new ErrorResponse();

        Error error = new Error();
        error.setType(Constants.FUNCTIONAL_ERROR);
        error.setCode(Constants.CODE_BAD_REQUEST_ERROR);
        ex.getBindingResult().getAllErrors().forEach(err ->
                error.setMessage(Constants.MESSAJE_RESPONSE_BAD_REQUEST
                        + Constants.OPEN_CORCHETE
                        + err.getDefaultMessage()
                        + Constants.CLOSE_CORCHETE)
        );
        errorResponse.setError(error);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BussinesException.class)
    public ResponseEntity<ErrorResponse> handleBussinesException(BussinesException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        Error error = new Error();
        error.setType(Constants.FUNCTIONAL_ERROR);
        error.setCode(Constants.CODE_BAD_REQUEST_ERROR);
        error.setMessage(ex.getMessage());
        errorResponse.setError(error);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DataBaseException.class)
    public ResponseEntity<ErrorResponse> handleDataBaseException(DataBaseException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        Error error = new Error();
        error.setType(ex.getType());
        error.setCode(ex.getCode());
        error.setMessage(Constants.MESSAGE_RESPONSE_ERROR_DATABASE
                + Constants.OPEN_CORCHETE
                + ex.getMessage()
                + Constants.CLOSE_CORCHETE);
        errorResponse.setError(error);

        return new ResponseEntity<>(errorResponse, ex.getHttpStatus());
    }

    @ExceptionHandler(InvalidHeaderException.class)
    public ResponseEntity<ErrorResponse> handleInvalidHeaderException(InvalidHeaderException ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        Error error = new Error();
        error.setType(Constants.FUNCTIONAL_ERROR);
        error.setCode(Constants.CODE_BAD_REQUEST_ERROR);
        error.setMessage(ex.getMessage());
        errorResponse.setError(error);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse();
        Error error = new Error();
        error.setType(Constants.FUNCTIONAL_ERROR);
        error.setCode(Constants.CODE_TECHNICAL_ERROR_GENERAL);
        error.setMessage(Constants.MESSAGE_RESPONSE_ERROR_GENERAL +
                Constants.OPEN_CORCHETE +
                ex.getMessage() +
                Constants.CLOSE_CORCHETE);
        errorResponse.setError(error);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}