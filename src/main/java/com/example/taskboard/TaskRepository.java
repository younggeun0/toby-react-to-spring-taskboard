package com.example.taskboard;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TaskRepository extends JpaRepository<Task, Long> {

    // 제목 부분 일치(선택) + 태그 이름 정확히 일치(선택). 둘 다 null이면 전체.
    // 태그는 JOIN 대신 exists로 걸러, 행이 부풀지 않게 하고 SQL에서 페이징되게 한다
    @Query("""
        select t from Task t
        where (:title is null or t.title like concat('%', :title, '%'))
          and (:tag is null or exists (select 1 from t.tags tg where tg.name = :tag))
        """)
    Page<Task> search(@Param("title") String title, @Param("tag") String tag, Pageable pageable);
}
