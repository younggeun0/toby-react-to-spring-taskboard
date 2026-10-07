package com.example.taskboard;

import jakarta.persistence.EntityManager;
import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

// @DataJpaTest는 테스트 메서드 하나를 트랜잭션 하나로 감싼다
@DataJpaTest
class PersistenceContextTest {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private EntityManager em;

    @Test
    void 한_트랜잭션에서_같은_id를_두_번_찾으면_같은_객체이고_SELECT는_한_번이다() {
        Long id = taskRepository.save(new Task("출석부 보기", null)).getId();
        em.flush();
        em.clear();  // 출석부를 비워, 아래 첫 조회가 DB까지 가게 한다

        Statistics stats = em.getEntityManagerFactory().unwrap(SessionFactory.class).getStatistics();
        stats.setStatisticsEnabled(true);
        stats.clear();

        Task t1 = taskRepository.findById(id).orElseThrow();
        Task t2 = taskRepository.findById(id).orElseThrow();

        assertThat(t1).isSameAs(t2);                          // t1 == t2
        assertThat(stats.getPrepareStatementCount()).isEqualTo(1);  // SELECT는 한 번
    }
}
