package com.fabiocaldas.tasks.api.controllers;

import com.fabiocaldas.tasks.api.dtos.CreateTaskDTO;
import com.fabiocaldas.tasks.api.dtos.ResponseTaskDTO;
import com.fabiocaldas.tasks.api.dtos.ToggleTaskPositionDTO;
import com.fabiocaldas.tasks.api.dtos.UpdateTaskDTO;
import com.fabiocaldas.tasks.api.entities.Task;
import com.fabiocaldas.tasks.api.services.TaskService;
import com.fabiocaldas.tasks.api.services.exceptions.ConflictException;
import com.fabiocaldas.tasks.api.services.exceptions.DTOEmptyException;
import com.fabiocaldas.tasks.api.services.exceptions.EntityNotFoundException;
import com.fabiocaldas.tasks.api.services.exceptions.ValidationException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @PostMapping
    public ResponseEntity<ResponseTaskDTO> create(@RequestBody @Valid CreateTaskDTO createTaskDTO) throws ConflictException {
        Task taskCreated = this.taskService.create(createTaskDTO);
        ResponseTaskDTO responseTaskDTO = new ResponseTaskDTO(taskCreated);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseTaskDTO);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ResponseTaskDTO>> getAll() {
        List<Task> tasks = this.taskService.getAll();
        List<ResponseTaskDTO> responseTaskDTO = tasks.stream().map(ResponseTaskDTO::new).toList();

        return ResponseEntity.ok(responseTaskDTO);
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<ResponseTaskDTO> update(
            @PathVariable int taskId,
            @RequestBody @Valid UpdateTaskDTO updateTaskDTO
    )
            throws DTOEmptyException, EntityNotFoundException, ConflictException {
        Task taskUpdated = this.taskService.update(taskId, updateTaskDTO);
        ResponseTaskDTO responseTaskDTO = new ResponseTaskDTO(taskUpdated);

        return ResponseEntity.ok(responseTaskDTO);
    }

    @PutMapping("/change_position")
    public ResponseEntity<Void> toggleIndex(
            @RequestBody @Valid ToggleTaskPositionDTO toggleTaskPositionDTO
    )
            throws ValidationException, ConflictException, EntityNotFoundException {
        this.taskService.togglePosition(toggleTaskPositionDTO);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> delete(@PathVariable int taskId) throws EntityNotFoundException {
        this.taskService.delete(taskId);

        return ResponseEntity.ok().build();
    }
}
