package com.example.aop.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class AccountNumberTest {

    @Test
    @DisplayName("계좌 번호를 생성한다.")
    void createAccountNumber() {
        // Given
        String expectedAccountNumber = "1111-2222-3333-4444";

        // When
        AccountNumber accountNumber = AccountNumber.from("1111-2222-3333-4444");

        // Then
        assertThat(accountNumber.getAccountNumber()).isEqualTo(expectedAccountNumber);
    }

    @Test
    @DisplayName("계좌 번호 equals 테스트")
    void equalsAccountNumberTest() {
        // Given
        AccountNumber accountNumber1 = AccountNumber.from("1111-2222-3333-4444");
        AccountNumber accountNumber2 = AccountNumber.from("1111-2222-3333-4444");

        // When && Then
        assertThat(accountNumber1).isEqualTo(accountNumber2);
    }
}
