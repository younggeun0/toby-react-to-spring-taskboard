package com.example.taskboard.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    public List<String> findAll() {
        return List.of("Spring 첫 엔드포인트 띄우기", "커피 마시기");
    }
}
