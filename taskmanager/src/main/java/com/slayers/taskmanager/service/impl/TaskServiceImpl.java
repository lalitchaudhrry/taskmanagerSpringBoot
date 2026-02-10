package com.slayers.taskmanager.service.impl;

import com.slayers.taskmanager.dto.TaskRequestDto;
import com.slayers.taskmanager.dto.TaskResponseDto;
import com.slayers.taskmanager.entity.Task;
import com.slayers.taskmanager.entity.TaskStatus;
import com.slayers.taskmanager.repository.TaskRepository;
import com.slayers.taskmanager.repository.UserRepository;
import com.slayers.taskmanager.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.slayers.taskmanager.entity.User;


import java.time.LocalDate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;


    @Override
    public TaskResponseDto createTask(TaskRequestDto dto) {

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Task task = Task.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .deadline(dto.getDeadline())
                .status(TaskStatus.TODO)
                .createdAt(LocalDate.now())
                .assignedUser(user)   // ⭐ VERY IMPORTANT
                .build();

        Task saved = taskRepository.save(task);

        return mapToResponse(saved);
    }




    @Override
    public TaskResponseDto getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));

        return mapToResponse(task);
    }

    @Override
    public void deleteTask(Long id) {

        if(!taskRepository.existsById(id)){
            throw new RuntimeException("Task not found with id: " + id);
        }

        taskRepository.deleteById(id);
    }

    @Override
    public TaskResponseDto updateTask(Long id, TaskRequestDto dto) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));

        // SAFE UPDATE
        if(dto.getTitle() != null){
            task.setTitle(dto.getTitle());
        }

        if(dto.getDescription() != null){
            task.setDescription(dto.getDescription());
        }

        if(dto.getDeadline() != null){
            task.setDeadline(dto.getDeadline());
        }

        Task updated = taskRepository.save(task);

        return mapToResponse(updated);
    }

    @Override
    public TaskResponseDto updateStatus(Long id, TaskStatus status) {

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));

        task.setStatus(status);

        Task updated = taskRepository.save(task);

        return mapToResponse(updated);
    }
    @Override
    public TaskResponseDto assignTask(Long taskId, Long userId) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + taskId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        task.setAssignedUser(user);

        return mapToResponse(taskRepository.save(task));
    }

    @Override
    public List<TaskResponseDto> getTasksByUser(Long userId) {

        if(!userRepository.existsById(userId)){
            throw new RuntimeException("User not found with id: " + userId);
        }

        return taskRepository.findByAssignedUserId(userId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }


    private TaskResponseDto mapToResponse(Task task) {
        return TaskResponseDto.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .deadline(task.getDeadline())
                .assignedUserId(
                        task.getAssignedUser() != null
                                ? task.getAssignedUser().getId()
                                : null
                )
                .build();
    }

}
