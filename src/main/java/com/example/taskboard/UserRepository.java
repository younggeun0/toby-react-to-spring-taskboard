package com.example.taskboard;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    // 사용자와 할 일을 JOIN 한 번으로 함께 읽는다
    @Query("select u from User u join fetch u.tasks")
    List<User> findAllWithTasks();

    // 컬렉션 JOIN FETCH + 페이징: Hibernate가 경고하고 메모리에서 자른다
    // 반환형을 Page가 아니라 List로 둬서 count 쿼리 없이 본문만 본다
    @Query("select u from User u join fetch u.tasks")
    List<User> findPageWithTasks(Pageable pageable);
}
