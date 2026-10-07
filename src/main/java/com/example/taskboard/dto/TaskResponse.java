package com.example.taskboard.dto;

import com.example.taskboard.Tag;
import com.example.taskboard.Task;

import java.util.List;

public record TaskResponse(Long id, String title, String description, boolean done, List<String> tags) {

    // tags는 지연 로딩이다. 트랜잭션 안에서 불러야 한다
    public static TaskResponse from(Task task) {
        List<String> tags = task.getTags().stream().map(Tag::getName).sorted().toList();
        return new TaskResponse(task.getId(), task.getTitle(), task.getDescription(), task.isDone(), tags);
    }
}
