package com.example.taskboard;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TaskSearchRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TagRepository tagRepository;

    @Test
    void 태그로_할일을_검색한다() {
        // given — "긴급" 태그가 달린 할 일과 안 달린 할 일을 섞어 저장
        Tag urgent = tagRepository.save(new Tag("긴급"));
        Tag study = tagRepository.save(new Tag("공부"));

        Task outage = new Task("서버 장애 대응");
        outage.addTag(urgent);
        outage.addTag(study);
        taskRepository.save(outage);

        Task jpa = new Task("JPA 익히기");
        jpa.addTag(study);
        taskRepository.save(jpa);

        taskRepository.save(new Task("러닝"));

        // when
        Page<Task> result = taskRepository.search(null, "긴급", PageRequest.of(0, 20));

        // then — "긴급" 태그가 달린 것만 나와야 한다
        assertThat(result.getContent())
                .extracting(Task::getTitle)
                .containsExactly("서버 장애 대응");
    }

    @Test
    void 제목_부분_일치와_태그를_함께_건다() {
        Tag study = tagRepository.save(new Tag("공부"));
        Task jpa = new Task("JPA 익히기");
        jpa.addTag(study);
        taskRepository.save(jpa);
        Task spring = new Task("Spring 익히기");
        spring.addTag(study);
        taskRepository.save(spring);
        taskRepository.save(new Task("JPA 책 반납"));

        Page<Task> result = taskRepository.search("JPA", "공부", PageRequest.of(0, 20));

        assertThat(result.getContent()).extracting(Task::getTitle).containsExactly("JPA 익히기");
        assertThat(result.getTotalElements()).isEqualTo(1);
    }
}
