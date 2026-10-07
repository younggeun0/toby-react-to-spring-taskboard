package com.example.taskboard.controller;

import com.example.taskboard.dto.TaskCreateRequest;
import com.example.taskboard.dto.TaskResponse;
import com.example.taskboard.service.TaskService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

    // 예: /api/tasks/search?title=서버&tag=긴급&page=0
    @GetMapping("/search")
    public Page<TaskResponse> search(@RequestParam(required = false) String title,
                                     @RequestParam(required = false) String tag,
                                     @PageableDefault(size = 20, sort = "id") Pageable pageable) {
        return taskService.search(title, tag, pageable);
    }

    @GetMapping("/{id}")
    public TaskResponse getTask(@PathVariable Long id) {
        return taskService.findById(id);
    }

    @GetMapping("/{id}/owner")
    public String getOwner(@PathVariable Long id) {
        return taskService.findOwnerName(id);
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
