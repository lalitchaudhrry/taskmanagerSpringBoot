package com.slayers.taskmanager.service;

import com.slayers.taskmanager.dto.TaskRequestDto;
import com.slayers.taskmanager.dto.TaskResponseDto;

import java.util.List;

public interface TaskService {

    TaskResponseDto createTask(TaskRequestDto dto);

    List<TaskResponseDto> getAllTasks();

    TaskResponseDto getTaskById(Long id);

    void deleteTask(Long id);
}
