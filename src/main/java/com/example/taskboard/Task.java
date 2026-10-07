package com.example.taskboard;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private boolean done;

    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;  // 연관관계의 주인. task 테이블의 user_id 칼럼

    // 할 일 하나에 태그 여러 개, 태그 하나도 여러 할 일에. 중간 테이블 task_tags가 둘을 잇는다
    @ManyToMany
    @JoinTable(name = "task_tags",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags = new LinkedHashSet<>();

    protected Task() {
        // JPA가 내부적으로 쓰는 기본 생성자
    }

    public Task(String title) {
        this(title, null);
    }

    public Task(String title, String description) {
        this.title = title;
        this.description = description;
        this.done = false;
        this.createdAt = LocalDateTime.now();
    }

    public Task(String title, String description, User user) {
        this(title, description);
        this.user = user;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public boolean isDone() { return done; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public User getUser() { return user; }

    void setUser(User user) { this.user = user; }  // User.addTask만 쓴다
    public Set<Tag> getTags() { return tags; }

    public void addTag(Tag tag) { tags.add(tag); }

    public void markDone() { this.done = true; }
}
