package com.example.taskboard;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByName(String name);  // 메서드 이름으로 쿼리를 만든다: where name = ?

    // 사용자와 할 일을 JOIN 한 번으로 함께 읽는다
    @Query("select u from User u join fetch u.tasks")
    List<User> findAllWithTasks();

    // 컬렉션 JOIN FETCH + 페이징: Hibernate가 경고하고 메모리에서 자른다
    // 반환형을 Page가 아니라 List로 둬서 count 쿼리 없이 본문만 본다
    @Query("select u from User u join fetch u.tasks")
    List<User> findPageWithTasks(Pageable pageable);

    // 필요한 값만 DTO로 바로 받는다. 엔티티를 거치지 않아 지연 로딩도 N+1도 없다
    @Query("""
        select new com.example.taskboard.UserSummary(u.name, count(t))
        from User u left join u.tasks t
        group by u.id, u.name
        """)
    List<UserSummary> findSummaries();
}
