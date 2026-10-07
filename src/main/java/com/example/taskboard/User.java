package com.example.taskboard;

import jakarta.persistence.CascadeType;
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

    private String password;  // BCrypt 해시만 저장한다. 평문은 어디에도 남기지 않는다

    // 주인은 Task.user. 이쪽은 거울이지만, User를 저장할 때 새 할 일도 함께 저장(PERSIST)되게 한다
    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
    private List<Task> tasks = new ArrayList<>();

    protected User() {}

    public User(String name) {
        this.name = name;
    }

    public User(String name, String encodedPassword) {
        this.name = name;
        this.password = encodedPassword;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getPassword() { return password; }
    public List<Task> getTasks() { return tasks; }

    // 양쪽을 함께 채우는 편의 메서드. 주인(Task.user)을 채워야 DB에 user_id가 들어간다
    public void addTask(Task task) {
        tasks.add(task);
        task.setUser(this);
    }
}
