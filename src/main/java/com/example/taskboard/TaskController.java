package com.example.taskboard;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TaskController {

    @GetMapping("/api/tasks")
    public List<String> getTasks() {
        return List.of("Spring 첫 엔드포인트 띄우기", "커피 마시기");
    }
}
