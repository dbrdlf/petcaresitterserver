package com.yukil.petcaresitterserver.repository;

import com.yukil.petcaresitterserver.entity.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
}
