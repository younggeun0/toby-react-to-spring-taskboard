package com.example.taskboard.controller;

import com.example.taskboard.service.TaskService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {  // 생성자로 주입받는다
        this.taskService = taskService;
    }

    @GetMapping("/api/tasks")
    public List<String> getTasks() {
        return taskService.findAll();
    }
}
