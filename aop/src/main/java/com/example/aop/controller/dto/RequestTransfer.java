package com.example.aop.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RequestTransfer(
    // TODO 로그인 처리 시 보내는 사람 제거
    @NotBlank
    String sender,
    @NotBlank
    String receiver,
    @NotNull
    Long amount
) {
}
