package com.example.aop.domain;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

@Getter
@Entity
@Table(name = "transaction")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @AttributeOverride(name = "accountNumber", column = @Column(name = "sender"))
    private AccountNumber sender;

    @Embedded
    @AttributeOverride(name = "accountNumber", column = @Column(name = "receiver"))
    private AccountNumber receiver;

    private Long amount;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    @CreatedDate
    private LocalDateTime createdAt;

    private Transaction(AccountNumber sender,
                       AccountNumber receiver,
                       Long amount,
                       TransactionType type,
                       TransactionStatus status) {
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        this.type = type;
        this.status = status;
    }

    public static Transaction transfer(Account sender, Account receiver, Long amount, TransactionStatus status) {
        return new Transaction(
            sender.getAccountNumber(),
            receiver.getAccountNumber(),
            amount,
            TransactionType.TRANSFER,
            status);
    }

    public void success() {
        this.status = TransactionStatus.SUCCESS;
    }

    public void fail() {
        this.status = TransactionStatus.FAILED;
    }
}
