package com.fabiocaldas.tasks.api.dtos;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Range;

public record ToggleTaskPositionDTO(
        @NotNull(message = "Campo obrigatório")
        @JsonAlias("task_id") int taskId,

        @NotNull(message = "Campo obrigatório")
        @Range(min = 1, message = "Position inválido")
        @JsonAlias("new_position") int newPosition
) {
}
