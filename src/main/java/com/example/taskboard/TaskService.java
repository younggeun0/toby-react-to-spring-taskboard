package com.example.taskboard;

import java.util.List;

public class TaskService {

    public List<String> findAll() {
        return List.of("Spring 첫 엔드포인트 띄우기", "커피 마시기");
    }
}
