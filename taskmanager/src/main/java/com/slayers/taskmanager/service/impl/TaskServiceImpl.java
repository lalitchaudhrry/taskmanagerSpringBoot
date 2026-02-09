package com.slayers.taskmanager.service.impl;

import com.slayers.taskmanager.dto.TaskRequestDto;
import com.slayers.taskmanager.dto.TaskResponseDto;
import com.slayers.taskmanager.entity.Task;
import com.slayers.taskmanager.entity.TaskStatus;
import com.slayers.taskmanager.repository.TaskRepository;
import com.slayers.taskmanager.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Override
    public TaskResponseDto createTask(TaskRequestDto dto) {

        Task task = Task.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .deadline(dto.getDeadline())
                .status(TaskStatus.TODO)
                .createdAt(LocalDateTime.now())
                .build();

        Task saved = taskRepository.save(task);

        return mapToResponse(saved);
    }

    @Override
    public List<TaskResponseDto> getAllTasks() {
        return taskRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public TaskResponseDto getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        return mapToResponse(task);
    }

    @Override
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    private TaskResponseDto mapToResponse(Task task) {
        return TaskResponseDto.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .deadline(task.getDeadline())
                .build();
    }
}
