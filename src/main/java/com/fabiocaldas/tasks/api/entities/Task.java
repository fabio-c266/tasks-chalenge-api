package com.fabiocaldas.tasks.api.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private BigDecimal price;
    private Integer position;

    @Column(name = "expire_at")
    private LocalDateTime expireAt;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createAt;
}
