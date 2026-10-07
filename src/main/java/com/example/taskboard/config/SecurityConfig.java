package com.example.taskboard.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// ⚠️ AI가 자신 있게 주는 구버전 코드 (Spring Security 5.x 이하)
// 이 단계는 컴파일되지 않는 것이 정상이다. 다음 단계(ch10-3)에서 6.x 방식으로 고친다
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeRequests()
                .antMatchers("/api/auth/**").permitAll()
                .anyRequest().authenticated()
            .and()
            .addFilterBefore(jwtFilter(),
                UsernamePasswordAuthenticationFilter.class);
    }
}
