package com.example.taskboard;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void DTO_프로젝션은_단_한_번의_쿼리로_요약을_가져온다() {
        // given — 사용자 2명, 각각 할 일 2개씩 미리 저장
        User a = userRepository.save(new User("아라"));
        a.addTask(new Task("JPA 익히기"));
        a.addTask(new Task("N+1 잡기"));
        User b = userRepository.save(new User("보검"));
        b.addTask(new Task("러닝"));
        b.addTask(new Task("스트레칭"));

        // when
        List<UserSummary> summaries = userRepository.findSummaries();

        // then — 결과가 의도한 대로인지 단언
        assertThat(summaries).hasSize(2);
        assertThat(summaries)
                .extracting(UserSummary::taskCount)
                .containsExactlyInAnyOrder(2L, 2L);
    }
}
