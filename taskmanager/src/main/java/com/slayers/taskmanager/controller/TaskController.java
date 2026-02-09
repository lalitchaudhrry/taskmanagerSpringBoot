package com.slayers.taskmanager.controller;

import com.slayers.taskmanager.dto.TaskRequestDto;
import com.slayers.taskmanager.dto.TaskResponseDto;
import com.slayers.taskmanager.entity.TaskStatus;
import com.slayers.taskmanager.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public TaskResponseDto createTask(@RequestBody TaskRequestDto dto) {
        return taskService.createTask(dto);
    }

    @GetMapping
    public List<TaskResponseDto> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/{id}")
    public TaskResponseDto getTask(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }

    @PutMapping("/{id}")
    public TaskResponseDto updateTask(
            @PathVariable Long id,
            @RequestBody TaskRequestDto dto) {

        return taskService.updateTask(id, dto);
    }


    @PatchMapping("/{id}/status")
    public TaskResponseDto updateStatus(
            @PathVariable Long id,
            @RequestParam TaskStatus status) {

        return taskService.updateStatus(id, status);
    }
}
