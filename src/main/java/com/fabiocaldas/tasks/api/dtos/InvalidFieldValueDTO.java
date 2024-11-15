package com.fabiocaldas.tasks.api.dtos;

public record InvalidFieldValueDTO(
        String field,
        String error
)
{}
