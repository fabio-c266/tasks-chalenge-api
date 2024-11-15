package com.fabiocaldas.tasks.api.configs;

import com.fabiocaldas.tasks.api.dtos.ExceptionResponseDTO;
import com.fabiocaldas.tasks.api.dtos.InvalidFieldValueDTO;
import com.fabiocaldas.tasks.api.services.exceptions.ConflictException;
import com.fabiocaldas.tasks.api.services.exceptions.DTOEmptyException;
import com.fabiocaldas.tasks.api.services.exceptions.EntityNotFoundException;
import com.fabiocaldas.tasks.api.services.exceptions.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class HandlerExceptions {
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ExceptionResponseDTO> handlerHttpMessageNotReadableException(Exception e) {
        return ResponseEntity.badRequest().body(new ExceptionResponseDTO("Body inválido"));
    }

    @ExceptionHandler(MissingPathVariableException.class)
    public ResponseEntity<ExceptionResponseDTO> handlerMissingPathVariableException(Exception e) {
        return ResponseEntity.badRequest().body(new ExceptionResponseDTO("Faltando parametros na URI"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponseDTO> handlerGenericException(Exception e) {
        System.out.println(e.getMessage());

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                new ExceptionResponseDTO("O servidor não pode processar a requisição, tente novamente mais tarde")
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<InvalidFieldValueDTO>> handlerMethodArgumentNotValid(MethodArgumentNotValidException except) {
        List<FieldError> errors = except.getFieldErrors();
        List<InvalidFieldValueDTO> errorsFormatted = errors.stream()
                .map(e -> new InvalidFieldValueDTO(e.getField(), e.getDefaultMessage())).toList();

        return ResponseEntity.badRequest().body(errorsFormatted);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ExceptionResponseDTO> handlerValidationException(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ExceptionResponseDTO(e.getMessage())
        );
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExceptionResponseDTO> handlerEntityNotFoundException(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ExceptionResponseDTO(e.getMessage())
        );
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ExceptionResponseDTO> handlerConflictException(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ExceptionResponseDTO(e.getMessage())
        );
    }

    @ExceptionHandler(DTOEmptyException.class)
    public ResponseEntity<ExceptionResponseDTO> handlerDTOEmptyException(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ExceptionResponseDTO(e.getMessage())
        );
    }
}
