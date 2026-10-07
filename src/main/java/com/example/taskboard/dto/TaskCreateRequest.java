package com.example.taskboard.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TaskCreateRequest(
    @NotBlank(message = "제목은 비어 있을 수 없습니다.")
    @Size(max = 100, message = "제목은 100자를 넘을 수 없습니다.")
    String title,

    @Size(max = 500, message = "설명은 500자를 넘을 수 없습니다.")
    String description
) {
}
