package com.example.aop.domain;

import com.example.aop.exception.TransferException;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Getter
@Entity
@Table(name = "account")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account {

    @EmbeddedId
    private AccountNumber accountNumber;

    private Long balance;

    @CreatedDate
    private LocalDateTime createAt;

    @LastModifiedDate
    private LocalDateTime updateAt;

    private Account(AccountNumber accountNumber) {
        this.accountNumber = accountNumber;
        this.balance = 0L;
    }

    public static Account createAccount(AccountNumber accountNumber) {
        return new Account(accountNumber);
    }

    public void withdraw(Long amount) {
        if (amount <= 0) {
            throw new TransferException("Amount must be greater than 0");
        }
        if (amount > balance) {
            throw new TransferException("amount exceeds balance");
        }
        balance -= amount;
    }

    public void deposit(Long amount) {
        balance += amount;
    }

    public void transfer(Account from, Long amount) {
        withdraw(amount);
        from.deposit(amount);
    }
}
