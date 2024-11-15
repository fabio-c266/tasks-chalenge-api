package com.fabiocaldas.tasks.api.services.exceptions;

public class ConflictException extends Exception {
    public ConflictException(String entityName, String field) {
        super("Você já possui uma " + entityName + " com esse " + field);
    }
}
