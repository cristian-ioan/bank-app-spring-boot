package com.banking.repository;

import com.banking.model.Account;
import com.banking.model.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends GenericRepository<Transaction> {
    List<Transaction> findTransactionsByAccount(Account account);
}
