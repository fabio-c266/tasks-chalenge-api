package com.fabiocaldas.tasks.api.dtos;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CreateTaskDTO(
        @NotBlank(message = "Campo obrigatório")
        @Size(min = 3, message = "No mínimo 3 caracteris")
        @Size(max = 80, message = "Precisa ser no máximo 80 caracteris")
        String name,

        @NotNull(message = "Campo obrigatório")
        @Range(min = 0, message = "O preço precisa ser no mínimo 0")
        BigDecimal price,

        @NotNull(message = "Campo obrigatório")
        @Future(message = "A data precisa estar no futuro")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        @JsonAlias("expire_at") LocalDateTime expireAt
)
{}
