package com.fabiocaldas.tasks.api.repositories;

import com.fabiocaldas.tasks.api.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    boolean existsByName(String name);

    @Query("""
            SELECT t FROM Task t
            order by t.position
            """)
    List<Task> findAllByPosition();

    @Query("""
            SELECT t.position FROM Task t
            ORDER BY t.position DESC
            LIMIT 1
            """)
    Optional<Integer> findLastPosition();


    @Query("""
            SELECT t FROM Task t 
            WHERE t.position BETWEEN :start AND :end
            """)
    List<Task> findByPositionBetween(int start, int end);
}
