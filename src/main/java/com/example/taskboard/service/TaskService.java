package com.example.taskboard.service;

import com.example.taskboard.Task;
import com.example.taskboard.TaskRepository;
import com.example.taskboard.dto.TaskCreateRequest;
import com.example.taskboard.dto.TaskResponse;
import com.example.taskboard.exception.TaskNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    // 4장에서 배운 생성자 주입 — Spring이 구현체를 건네준다
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<TaskResponse> findAll() {
        return taskRepository.findAll().stream()   // 메모리에서 꺼내던 자리
                .map(TaskResponse::from)
                .toList();
    }

    public TaskResponse create(TaskCreateRequest request) {
        Task task = new Task(request.title(), request.description());
        return TaskResponse.from(taskRepository.save(task));   // 메모리에 넣던 자리. id는 DB가 정한다
    }

    public TaskResponse findById(Long id) {
        return taskRepository.findById(id)
                .map(TaskResponse::from)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }
}
