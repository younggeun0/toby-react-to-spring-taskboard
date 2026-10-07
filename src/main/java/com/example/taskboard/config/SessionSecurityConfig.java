package com.example.taskboard.config;

import com.example.taskboard.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SessionSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // CORS를 Security 필터체인 안에서 처리한다. CorsConfig의 corsConfigurationSource 빈을 쓴다
            .cors(Customizer.withDefaults())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/login").permitAll()
                .requestMatchers("/api/tasks/**").authenticated()
                .anyRequest().permitAll()
            )
            // 세션을 필요할 때 만든다 — JWT 때의 STATELESS와 정반대
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
            )
            .formLogin(form -> {});  // 폼 로그인을 켜두면 세션 발급까지 Security가 맡는다

        return http.build();
    }

    // 폼 로그인이 DB의 사용자로 로그인하게 한다. 비밀번호 대조는 Security가 PasswordEncoder로 한다
    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return username -> userRepository.findByName(username)
                .map(u -> User.withUsername(u.getName()).password(u.getPassword()).build())
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
