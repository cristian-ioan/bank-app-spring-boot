package com.banking.service;

import com.banking.dto.AccountDTO;
import com.banking.exception.WrongTokenException;
import com.banking.model.Account;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AccountService {
    Account findById(Long id);
    List<Account> findAll();
    Account createAccount(Account account);
    Account updateAccount(Account account);
    void deleteAccount(Account account);

    List<AccountDTO> getAccountsByToken(String token) throws WrongTokenException;
    AccountDTO createAccountByToken(String token, Account account) throws WrongTokenException;
}
