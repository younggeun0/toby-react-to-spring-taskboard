package com.example.taskboard.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:3000"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    // 5장: Security가 없을 때는 위 빈을 아무도 읽지 않아서, 필터로 감싸 요청 앞단에 걸었다.
    // 9장부터는 Spring Security도 위 빈을 찾아 필터체인 안에서 CORS를 처리한다. 같은 설정이라 두 번 처리돼도 결과는 같다.
    // corsFilter(CorsConfigurationSource source)처럼 매개변수로 받으면, Spring MVC가 이미 같은 타입의 빈을
    // 하나 들고 있어 "2개 발견" 오류로 시작이 실패한다. 그래서 같은 클래스의 메서드를 직접 부른다.
    @Bean
    public CorsFilter corsFilter() {
        return new CorsFilter(corsConfigurationSource());
    }
}
