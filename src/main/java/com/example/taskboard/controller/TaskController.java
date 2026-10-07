package com.example.taskboard.controller;

import com.example.taskboard.Task;
import com.example.taskboard.dto.TaskCreateRequest;
import com.example.taskboard.dto.TaskResponse;
import com.example.taskboard.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<TaskResponse> getTasks() {
        return taskService.findAll();
    }

    @GetMapping("/{id}")
    public TaskResponse getTask(@PathVariable Long id) {
        return taskService.findById(id);
    }

    @GetMapping("/{id}/owner")
    public String getOwner(@PathVariable Long id) {
        Task task = taskService.findTaskEntity(id);
        // 이 시점엔 task 테이블만 읽었다. user는 아직 안 읽은 프록시다
        return task.getUser().getName();  // 이름을 실제로 꺼내는 순간 users를 조회한다
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<Void> complete(@PathVariable Long id) {
        taskService.completeTask(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<TaskResponse> create(@Valid @RequestBody TaskCreateRequest request) {
        TaskResponse created = taskService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}
