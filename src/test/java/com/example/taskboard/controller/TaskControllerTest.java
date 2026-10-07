package com.example.taskboard.controller;

import com.example.taskboard.dto.TaskCreateRequest;
import com.example.taskboard.dto.TaskResponse;
import com.example.taskboard.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// 9장부터 보안 필터도 함께 뜬다. 인증된 사용자로(@WithMockUser), CSRF 토큰을 실어(csrf()) 요청한다
@WithMockUser
@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean  // 웹 계층만 뜨는 테스트에 가짜 TaskService를 빈으로 넣는다
    private TaskService taskService;

    @Test
    void 제목이_비어있으면_400을_돌려준다() throws Exception {
        String body = """
            { "title": "", "description": "설명" }
            """;

        mockMvc.perform(post("/api/tasks").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.fieldErrors.title").exists());
    }

    @Test
    void 정상_요청이면_201과_만든_할_일을_돌려준다() throws Exception {
        given(taskService.create(any(TaskCreateRequest.class)))
            .willReturn(new TaskResponse(1L, "Spring 공부", "5장", false));

        String body = """
            { "title": "Spring 공부", "description": "5장" }
            """;

        mockMvc.perform(post("/api/tasks").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.title").value("Spring 공부"));
    }
}
