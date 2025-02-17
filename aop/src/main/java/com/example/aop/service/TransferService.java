package com.example.aop.service;

import com.example.aop.controller.dto.RequestTransfer;
import com.example.aop.domain.Account;
import com.example.aop.domain.AccountNumber;
import com.example.aop.domain.Transaction;
import com.example.aop.domain.TransactionStatus;
import com.example.aop.exception.TransferException;
import com.example.aop.repository.AccountRepository;
import com.example.aop.repository.TransactionRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransferService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    @Transactional(noRollbackFor = TransferException.class)
    public Long transfer(@Valid RequestTransfer requestTransfer) {
        // TODO 로그인 처리 시 보내는 사람 제거
        Account sender = accountRepository.findById(AccountNumber.from(requestTransfer.sender()))
            .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 계좌번호 입니다. [ " + requestTransfer.receiver() + " ]"));
        Account receiver = accountRepository.findById(AccountNumber.from(requestTransfer.receiver()))
            .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 계좌번호 입니다. [ " + requestTransfer.receiver() + " ]"));

        Transaction transaction = recordTransferTransactionHistory(requestTransfer, sender, receiver);

        try {
            sender.transfer(receiver, requestTransfer.amount());
        } catch (TransferException e) {
            transaction.fail();
            log.error("송금 실패: {}", e.getMessage());
            throw e;
        }

        transaction.success();
        return sender.getBalance();
    }

    private Transaction recordTransferTransactionHistory(RequestTransfer requestTransfer, Account sender, Account receiver) {
        Transaction transaction = Transaction.transfer(sender, receiver, requestTransfer.amount(), TransactionStatus.PENDING);
        return transactionRepository.save(transaction);
    }
}
