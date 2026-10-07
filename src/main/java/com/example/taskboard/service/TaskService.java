package com.example.taskboard.service;

import com.example.taskboard.Tag;
import com.example.taskboard.TagRepository;
import com.example.taskboard.Task;
import com.example.taskboard.TaskRepository;
import com.example.taskboard.dto.TaskCreateRequest;
import com.example.taskboard.dto.TaskResponse;
import com.example.taskboard.exception.TaskNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final TagRepository tagRepository;

    // 4장에서 배운 생성자 주입 — Spring이 구현체를 건네준다
    public TaskService(TaskRepository taskRepository, TagRepository tagRepository) {
        this.taskRepository = taskRepository;
        this.tagRepository = tagRepository;
    }

    @Transactional(readOnly = true)  // TaskResponse.from이 태그를 지연 로딩한다
    public List<TaskResponse> findAll() {
        return taskRepository.findAll().stream()   // 메모리에서 꺼내던 자리
                .map(TaskResponse::from)
                .toList();
    }

    @Transactional
    public TaskResponse create(TaskCreateRequest request) {
        Task task = new Task(request.title(), request.description());
        if (request.tags() != null) {
            request.tags().stream().distinct().forEach(name -> task.addTag(findOrCreateTag(name)));
        }
        return TaskResponse.from(taskRepository.save(task));   // 메모리에 넣던 자리. id는 DB가 정한다
    }

    // 같은 이름의 태그는 재사용한다
    private Tag findOrCreateTag(String name) {
        return tagRepository.findByName(name)
                .orElseGet(() -> tagRepository.save(new Tag(name)));
    }

    @Transactional(readOnly = true)
    public Page<TaskResponse> search(String title, String tag, Pageable pageable) {
        return taskRepository.search(blankToNull(title), blankToNull(tag), pageable)
                .map(TaskResponse::from);  // 트랜잭션 안에서 DTO로 바꾼다
    }

    private static String blankToNull(String s) {
        return s == null || s.isBlank() ? null : s.trim();
    }

    @Transactional(readOnly = true)
    public TaskResponse findById(Long id) {
        return taskRepository.findById(id)
                .map(TaskResponse::from)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    @Transactional
    public void completeTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        task.markDone();   // done = true 로 바꾸기만 했다
        // save() 를 부르지 않았다!
    }

    // 지연 로딩은 트랜잭션(출석부)이 열려 있는 이 안에서 끝내고, 필요한 값만 꺼내 돌려준다
    @Transactional(readOnly = true)
    public String findOwnerName(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        return task.getUser() == null ? null : task.getUser().getName();  // POST로 만든 할 일은 주인이 없다
    }
}
