package com.banking.repository;

import com.banking.model.Account;
import com.banking.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends GenericRepository<Account> {
    List<Account> findAccountsByUserId(long id);
}
