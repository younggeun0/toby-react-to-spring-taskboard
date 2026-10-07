package com.example.taskboard.config;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

// 스프링을 띄우지 않는 순수 단위 테스트
class PasswordEncoderTest {

    private final PasswordEncoder encoder = new BCryptPasswordEncoder();

    @Test
    void 같은_비밀번호도_인코딩할_때마다_해시가_다르고_matches로는_둘_다_맞다() {
        String first = encoder.encode("pass1234");
        String second = encoder.encode("pass1234");

        System.out.println(first);
        System.out.println(second);

        assertThat(first).isNotEqualTo(second);           // 솔트가 매번 달라서
        assertThat(first).startsWith("$2a$10$");          // 알고리즘·비용·솔트가 해시 안에 들어 있다
        assertThat(encoder.matches("pass1234", first)).isTrue();
        assertThat(encoder.matches("pass1234", second)).isTrue();
        assertThat(encoder.matches("wrong", first)).isFalse();
    }
}
