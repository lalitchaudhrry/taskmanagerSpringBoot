package com.slayers.taskmanager.service;

import com.slayers.taskmanager.dto.TaskRequestDto;
import com.slayers.taskmanager.dto.TaskResponseDto;
import com.slayers.taskmanager.entity.TaskStatus;

import java.util.List;

public interface TaskService {

    TaskResponseDto createTask(TaskRequestDto dto);

    List<TaskResponseDto> getAllTasks();

    TaskResponseDto getTaskById(Long id);

    void deleteTask(Long id);

    TaskResponseDto updateTask(Long id, TaskRequestDto dto);

    TaskResponseDto updateStatus(Long id, TaskStatus status);

    TaskResponseDto assignTask(Long taskId, Long userId);
    List<TaskResponseDto> getTasksByUser(Long userId);


}
