package com.example.taskboard;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TaskController {

    private final TaskService taskService = new TaskService(); // 직접 만들기

    @GetMapping("/api/tasks")
    public List<String> getTasks() {
        return taskService.findAll();
    }
}
