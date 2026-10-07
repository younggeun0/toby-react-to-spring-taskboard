package com.example.taskboard;

// 사용자 이름과 할 일 개수. 8장 JPQL의 new 표현식이 이 패키지 경로를 그대로 쓴다
public record UserSummary(String name, long taskCount) {
}
