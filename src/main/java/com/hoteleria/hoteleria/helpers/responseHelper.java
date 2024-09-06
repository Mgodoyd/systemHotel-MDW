package com.hoteleria.hoteleria.helpers;

import org.springframework.http.HttpStatus;

/*request response structure */
public class responseHelper<T> {
    private String message;
    private HttpStatus status;
    private T data;
    private Object errors;

    public responseHelper(String message, HttpStatus status, T data, Object errors) {
        this.message = message;
        this.status = status;
        this.data = data;
        this.errors = errors;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Object getErrors() {
        return errors;
    }

    public void setErrors(Object errors) {
        this.errors = errors;
    }
}
