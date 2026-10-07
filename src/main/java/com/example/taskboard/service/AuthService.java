package com.example.taskboard.service;

import com.example.taskboard.User;
import com.example.taskboard.UserRepository;
import com.example.taskboard.security.JwtTokenProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                       JwtTokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    // 비밀번호 검증 + 토큰 발급
    @Transactional(readOnly = true)
    public String login(String username, String rawPassword) {
        User user = userRepository.findByName(username)
                .filter(u -> passwordEncoder.matches(rawPassword, u.getPassword()))
                // 사용자가 없는지, 비밀번호가 틀렸는지 구분해 알려 주지 않는다
                .orElseThrow(() -> new BadCredentialsException("아이디 또는 비밀번호가 올바르지 않습니다."));
        return tokenProvider.createToken(user.getName());
    }
}
