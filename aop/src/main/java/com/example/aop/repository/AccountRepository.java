package com.example.aop.repository;

import com.example.aop.domain.Account;
import com.example.aop.domain.AccountNumber;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, AccountNumber> {
}
