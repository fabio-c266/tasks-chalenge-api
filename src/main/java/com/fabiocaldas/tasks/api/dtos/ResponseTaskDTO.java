package com.fabiocaldas.tasks.api.dtos;

import com.fabiocaldas.tasks.api.entities.Task;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ResponseTaskDTO(
        int id,
        String name,
        BigDecimal price,
        int position,
        LocalDateTime expire_at,
        LocalDateTime created_at
) {
    public ResponseTaskDTO(Task task) {
        this(
                task.getId(),
                task.getName(),
                task.getPrice(),
                task.getPosition(),
                task.getExpireAt(),
                task.getCreateAt()
        );
    }
}
