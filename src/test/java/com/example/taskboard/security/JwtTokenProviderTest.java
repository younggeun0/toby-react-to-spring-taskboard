package com.example.taskboard.security;

import io.jsonwebtoken.security.SignatureException;
import io.jsonwebtoken.security.WeakKeyException;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class JwtTokenProviderTest {

    private static final String SECRET = "taskboard-local-dev-secret-change-me!!";  // application.yml과 같은 값

    @Test
    void 토큰을_만들고_다시_읽으면_사용자_이름이_나온다() {
        JwtTokenProvider provider = new JwtTokenProvider(SECRET);

        String token = provider.createToken("ara");
        System.out.println("token = " + token);

        String[] parts = token.split("\\.");
        assertThat(parts).hasSize(3);  // 헤더.페이로드.서명
        System.out.println("header  = " + decode(parts[0]));
        System.out.println("payload = " + decode(parts[1]));

        assertThat(decode(parts[0])).contains("\"alg\":\"HS256\"");
        assertThat(decode(parts[1])).contains("\"sub\":\"ara\"");
        assertThat(provider.getUsername(token)).isEqualTo("ara");
    }

    @Test
    void 다른_키로_서명한_토큰은_검증에_실패한다() {
        String forged = new JwtTokenProvider("another-secret-that-is-also-32-bytes!!").createToken("ara");

        assertThatThrownBy(() -> new JwtTokenProvider(SECRET).getUsername(forged))
            .isInstanceOf(SignatureException.class);
    }

    @Test
    void 키가_32바이트보다_짧으면_만들_때부터_실패한다() {
        assertThatThrownBy(() -> new JwtTokenProvider("too-short"))
            .isInstanceOf(WeakKeyException.class);
    }

    private static String decode(String part) {
        return new String(Base64.getUrlDecoder().decode(part), StandardCharsets.UTF_8);
    }
}
