package com.fabiocaldas.tasks.api.services.exceptions;

public class DTOEmptyException extends Exception {
    public DTOEmptyException() {
        super("Preencha por o menos um campo para atualizar algum registro");
    }
}
