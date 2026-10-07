package com.example.taskboard.dto;

import com.example.taskboard.Task;

public record TaskResponse(Long id, String title, String description, boolean done) {

    public static TaskResponse from(Task task) {
        return new TaskResponse(task.getId(), task.getTitle(), task.getDescription(), task.isDone());
    }
}
