package com.fabiocaldas.tasks.api.services.exceptions;

public class EntityNotFoundException extends Exception {
    public EntityNotFoundException(String name) {
        super(name + " não encontrada");
    }
}
