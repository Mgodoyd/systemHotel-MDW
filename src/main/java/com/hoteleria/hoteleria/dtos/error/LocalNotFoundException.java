package com.hoteleria.hoteleria.dtos.error;

/**
 * Exception thrown when a specified local entity is not found.
 * This exception is used to indicate that a search for a local entity
 * has failed because the entity does not exist.
 * 
 * @param message The detail message explaining the reason for the exception.
 */
public class LocalNotFoundException extends Exception {
    public LocalNotFoundException(String message) {
        super(message);
    }
}