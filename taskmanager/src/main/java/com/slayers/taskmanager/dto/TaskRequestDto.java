package com.slayers.taskmanager.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;


@Data
public class TaskRequestDto {

    private String title;
    private String description;
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate deadline;
    private Long userId;

}
