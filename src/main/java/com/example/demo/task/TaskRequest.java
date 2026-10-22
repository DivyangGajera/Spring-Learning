package com.example.demo.task;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TaskRequest(
    @NotBlank(message = "Task Title cannot be blank")
    
    @Size(max = 140,message = "Task Title cannot exceed 140 characters") 
        String title,
    @Size(max = 500,message = "Task Description cannot exceed 500 characters") 
        String description,

        LocalDateTime createdAt,
        LocalDateTime completedAt,
        boolean isCompleted) {

}
