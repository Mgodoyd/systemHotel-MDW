package com.hoteleria.hoteleria.dtos.error;

import org.modelmapper.spi.ErrorMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.hoteleria.hoteleria.helpers.responseHelper;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler implements Serializable {

    /**
     * Handles LocalNotFoundException and returns a ResponseEntity with an error
     * message and a NOT_FOUND status.
     *
     * @param exception the LocalNotFoundException thrown when a local resource is
     *                  not found
     * @return a ResponseEntity containing an ErrorMessage and a NOT_FOUND HTTP
     *         status
     */
    @ExceptionHandler(LocalNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorMessage> localNotFoundException(LocalNotFoundException exception) {
        ErrorMessage message = new ErrorMessage(exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

    /**
     * Handles MethodArgumentNotValidException thrown when a method argument fails
     * validation.
     *
     * @param ex      the MethodArgumentNotValidException that was thrown
     * @param headers the HTTP headers to be written to the response
     * @param status  the HTTP status code to be written to the response
     * @param request the current web request
     * @return a ResponseEntity containing the error details and a BAD_REQUEST
     *         status
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, Object> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new responseHelper<>("Error", HttpStatus.BAD_REQUEST, null, errors));
    }
}