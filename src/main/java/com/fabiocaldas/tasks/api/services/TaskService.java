package com.fabiocaldas.tasks.api.services;

import com.fabiocaldas.tasks.api.dtos.CreateTaskDTO;
import com.fabiocaldas.tasks.api.dtos.ToggleTaskPositionDTO;
import com.fabiocaldas.tasks.api.dtos.UpdateTaskDTO;
import com.fabiocaldas.tasks.api.entities.Task;
import com.fabiocaldas.tasks.api.repositories.TaskRepository;
import com.fabiocaldas.tasks.api.services.exceptions.ConflictException;
import com.fabiocaldas.tasks.api.services.exceptions.DTOEmptyException;
import com.fabiocaldas.tasks.api.services.exceptions.EntityNotFoundException;
import com.fabiocaldas.tasks.api.services.exceptions.ValidationException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    public Task create(CreateTaskDTO createTaskDTO) throws ConflictException {
        if (this.taskRepository.existsByName(createTaskDTO.name())) {
            throw new ConflictException("tarefa", "nome");
        }

        int lastIndex = this.taskRepository.findLastPosition().orElse(0);
        int taskIndex = lastIndex + 1;

        Task newTask = new Task(
                null,
                createTaskDTO.name(),
                createTaskDTO.price(),
                taskIndex,
                createTaskDTO.expireAt(),
                null
        );

        return this.taskRepository.save(newTask);
    }

    public List<Task> getAll() {
        return this.taskRepository.findAllByPosition();
    }

    @Transactional
    public Task update(int taskId, UpdateTaskDTO updateTaskDTO) throws EntityNotFoundException, DTOEmptyException, ConflictException {
        if (updateTaskDTO.name() == null
                && updateTaskDTO.price() == null
                && updateTaskDTO.expireAt() == null
        ) throw new DTOEmptyException();

        Task task = this.get(taskId);

        if (updateTaskDTO.name() != null) {
            if (this.taskRepository.existsByName(updateTaskDTO.name())) throw new ConflictException("tarefa", "nome");

            task.setName(updateTaskDTO.name());
        }

        if (updateTaskDTO.price() != null) task.setPrice(updateTaskDTO.price());
        if (updateTaskDTO.expireAt() != null) task.setExpireAt(updateTaskDTO.expireAt());

        return task;
    }

    public Task get(int taskId) throws EntityNotFoundException {
        return this.taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Tarefa"));
    }

    @Transactional
    public void togglePosition(ToggleTaskPositionDTO toggleTaskPositionDTO) throws EntityNotFoundException, ConflictException, ValidationException {
        Task task = this.get(toggleTaskPositionDTO.taskId());
        int positionDestination = toggleTaskPositionDTO.newPosition();

        if (task.getPosition().equals(positionDestination)) throw new ConflictException("tarefa", "posição");

        int lastPosition = this.taskRepository.findLastPosition().orElse(0);
        if (lastPosition < 2)
            throw new ValidationException("É necessário por o menos 2 tarefas para alterar a posição");

        if (positionDestination > lastPosition)
            throw new ValidationException("A posição máxima possível é " + lastPosition);

        if (task.getPosition() > positionDestination) {
            shiftTasksDown(positionDestination, task.getPosition() - 1);
        } else {
            shiftTasksUp(task.getPosition() + 1, positionDestination);
        }

        task.setPosition(positionDestination);
    }

    public void delete(int taskId) throws EntityNotFoundException {
        Task task = this.get(taskId);

        int lastPosition = this.taskRepository.findLastPosition().orElse(0);
        shiftTasksUp(task.getPosition() + 1, lastPosition);

        this.taskRepository.deleteById(task.getId());
    }

    private void shiftTasksDown(int start, int end) {
        List<Task> tasksToShift = this.taskRepository.findByPositionBetween(start, end);
        for (Task task : tasksToShift) {
            task.setPosition(task.getPosition() + 1);
        }
    }

    private void shiftTasksUp(int start, int end) {
        List<Task> tasksToShift = this.taskRepository.findByPositionBetween(start, end);
        for (Task task : tasksToShift) {
            task.setPosition(task.getPosition() - 1);
        }
    }
}
