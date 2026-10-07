package com.example.taskboard;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private boolean done;

    protected Task() {
        // JPA가 내부적으로 쓰는 기본 생성자
    }

    public Task(String title, String description) {
        this.title = title;
        this.description = description;
        this.done = false;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public boolean isDone() { return done; }

    public void markDone() { this.done = true; }
}
