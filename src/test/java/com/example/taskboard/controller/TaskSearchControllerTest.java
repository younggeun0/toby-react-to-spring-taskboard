package com.example.taskboard.controller;

import com.example.taskboard.dto.TaskResponse;
import com.example.taskboard.security.JwtTokenProvider;
import com.example.taskboard.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
class TaskSearchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // (TaskService는 5장에서처럼 가짜로 채워준다. @MockBean이 아니라 @MockitoBean)
    @MockitoBean
    private TaskService taskService;

    @MockitoBean  // 10장의 JwtAuthenticationFilter가 요구한다
    private JwtTokenProvider jwtTokenProvider;

    @Test
    @WithMockUser  // 9장부터 보안 필터체인도 함께 뜬다. 인증된 사용자로 요청한다
    void 검색어로_조회하면_200과_결과를_돌려준다() throws Exception {
        given(taskService.search(eq("서버"), any(), any()))
                .willReturn(new PageImpl<>(List.of(
                        new TaskResponse(1L, "서버 장애 대응", null, false, List.of("긴급")))));

        mockMvc.perform(get("/api/tasks/search")
                        .param("title", "서버")
                        .param("page", "0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[0].title").value("서버 장애 대응"));
    }
}
