package com.slayers.taskmanager.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class TaskRequestDto {

    private String title;
    private String description;
    private LocalDateTime deadline;
}
