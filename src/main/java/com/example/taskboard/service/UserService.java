package com.example.taskboard.service;

import com.example.taskboard.User;
import com.example.taskboard.UserRepository;
import com.example.taskboard.UserSummary;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<UserSummary> summarize() {
        List<User> users = userRepository.findAllWithTasks();  // ① 할 일까지 한 번에
        return users.stream()
                .map(u -> new UserSummary(
                        u.getName(),
                        u.getTasks().size()))               // ②
                .toList();
    }

    @Transactional(readOnly = true)
    public List<UserSummary> summarizePage(Pageable pageable) {
        return userRepository.findPageWithTasks(pageable).stream()
                .map(u -> new UserSummary(u.getName(), u.getTasks().size()))
                .toList();
    }
}
