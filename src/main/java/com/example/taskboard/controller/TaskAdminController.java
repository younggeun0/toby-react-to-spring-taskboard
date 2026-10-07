package com.example.taskboard.controller;

import com.example.taskboard.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/tasks")
public class TaskAdminController {

    private final TaskService taskService;

    public TaskAdminController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("tasks", taskService.findAll());
        return "tasks/list";
    }
}
