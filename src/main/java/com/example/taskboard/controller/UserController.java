package com.example.taskboard.controller;

import com.example.taskboard.UserSummary;
import com.example.taskboard.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/summary")
    public List<UserSummary> summary() {
        return userService.summarize();
    }
}
