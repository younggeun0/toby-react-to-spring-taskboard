package com.example.taskboard;

// 할 일 하나. 6장에서 JPA 엔티티가 되기 전까지는 메모리에만 있다
public class Task {

    private final Long id;
    private final String title;
    private final String description;
    private boolean done;

    public Task(Long id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public boolean isDone() {
        return done;
    }
}
