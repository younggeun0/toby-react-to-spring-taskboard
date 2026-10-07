package com.example.taskboard.service;

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
        return userRepository.findSummaries();  // DTO 프로젝션: 집계까지 SQL 한 번
    }

    @Transactional(readOnly = true)
    public List<UserSummary> summarizePage(Pageable pageable) {
        return userRepository.findPageWithTasks(pageable).stream()
                .map(u -> new UserSummary(u.getName(), u.getTasks().size()))
                .toList();
    }
}
