package com.example.aop.domain;

import lombok.Getter;

@Getter
public enum TransactionType {

    TRANSFER("이체"),
    DEPOSIT("입금"),
    WITHDRAWAL("출금"),
    ;

    TransactionType(String description) {
        this.description = description;
    }

    private final String description;
}
