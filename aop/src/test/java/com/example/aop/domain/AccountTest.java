package com.example.aop.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AccountTest {

    private Account account;

    @BeforeEach
    void setUp() {
        account = Account.createAccount(AccountNumber.from("10-123"));
    }

    @Test
    @DisplayName("계좌 입금 테스트")
    void successDeposit() {
        // When
        account.deposit(3_000L);

        // Then
        assertThat(account.getBalance()).isEqualTo(3_000L);
    }

    @Test
    @DisplayName("계좌 출금 성공 테스트")
    void successWithdrawal() {
        // Given
        Account account = Account.createAccount(AccountNumber.from("10-123"));
        account.deposit(3_000L);

        // When
        account.withdraw(1_000L);

        // Then
        assertThat(account.getBalance()).isEqualTo(3_000L - 1_000L);
    }

    @Test
    @DisplayName("계좌 출금 실패 테스트 (잔고보다 출금하려는 금액이 더 클 경우)")
    void failWithdrawal() {
        // Given
        Account account = Account.createAccount(AccountNumber.from("10-123"));
        account.deposit(2_000L);

        // When && Then
        assertThrows(IllegalArgumentException.class, () -> {
            account.withdraw(999_999L);
        });
    }

    @Test
    @DisplayName("계좌 출금 실패 테스트 (출금하려는 금액이 0원일 경우)")
    void failWithdrawalZero() {
        // Given
        Account account = Account.createAccount(AccountNumber.from("10-123"));

        // When && Then
        assertThrows(IllegalArgumentException.class, () -> {
            account.withdraw(0L);
        });
    }

    @Test
    @DisplayName("송금 성공 테스트")
    void successTransferAToB() {
        // Given
        Account anotherAccount = Account.createAccount(AccountNumber.from("10-321"));

        account.deposit(3_000L);

        // When
        account.transfer(anotherAccount, 1_000L);

        // Then
        assertThat(account.getBalance()).isEqualTo(3_000L - 1_000L);
        assertThat(anotherAccount.getBalance()).isEqualTo(1_000L);
    }

}
