package com.slayers.taskmanager.dto;

import com.slayers.taskmanager.entity.TaskStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;


@Data
@Builder
public class TaskResponseDto {

    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
    private LocalDate deadline;
    private Long assignedUserId;

}
