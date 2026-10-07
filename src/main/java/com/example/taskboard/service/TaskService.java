package com.example.taskboard.service;

import com.example.taskboard.Task;
import com.example.taskboard.dto.TaskCreateRequest;
import com.example.taskboard.dto.TaskResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicLong;

// 6장에서 DB를 붙이기 전까지 메모리에 저장한다(id 순 정렬). 서버를 끄면 사라진다
@Service
public class TaskService {

    private final Map<Long, Task> tasks = new ConcurrentSkipListMap<>();
    private final AtomicLong sequence = new AtomicLong();

    public List<TaskResponse> findAll() {
        return tasks.values().stream().map(TaskResponse::from).toList();
    }

    public TaskResponse create(TaskCreateRequest request) {
        Long id = sequence.incrementAndGet();  // id는 서버가 정한다
        Task task = new Task(id, request.title(), request.description());
        tasks.put(id, task);
        return TaskResponse.from(task);
    }
}
