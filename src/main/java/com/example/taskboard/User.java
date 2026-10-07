package com.example.taskboard;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")  // H2 2.x에서 USER는 예약어라 테이블 이름을 바꾼다
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "user")  // 주인은 Task.user. 이쪽은 읽기 전용 거울
    private List<Task> tasks = new ArrayList<>();

    protected User() {}

    public User(String name) {
        this.name = name;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public List<Task> getTasks() { return tasks; }
}
