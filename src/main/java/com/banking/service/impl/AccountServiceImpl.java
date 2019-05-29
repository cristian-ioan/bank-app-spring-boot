package com.banking.service.impl;

import com.banking.dto.AccountDTO;
import com.banking.exception.WrongTokenException;
import com.banking.model.*;
import com.banking.repository.AccountRepository;
import com.banking.repository.AuthenticationRepository;
import com.banking.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("accountService")
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AuthenticationRepository authenticationRepository;

    @Override
    public Account findById(Long id) {
        return accountRepository.findById(id);
    }

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    @Transactional
    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    @Override
    @Transactional
    public Account updateAccount(Account account) {
        return accountRepository.save(account);
    }

    @Override
    @Transactional
    public void deleteAccount(Account account) {
        accountRepository.delete(account);
    }

    @Override
    public List<AccountDTO> getAccountsByToken(String token) throws WrongTokenException {
        Authentication authentication = authenticationRepository.findAuthenticationByToken(token);
        if (authentication == null){
            throw new WrongTokenException("Wrong token!");
        }
        User user = authentication.getUser();
        List<Account> accountList = accountRepository.findAccountsByUserId(user.getId());
        List<AccountDTO> accountsDTOList = new ArrayList<>();
        for(Account account : accountList){
            accountsDTOList.add(new AccountDTO(account.getAccount_Number(), account.getAccount_Type(), account.getBalance(),
                    account.getCreatedTime(), account.getUpdatedTime()));
        }
        return accountsDTOList;
    }

    @Override
    @Transactional
    public AccountDTO createAccountByToken(String token, Account account) throws WrongTokenException {
        Authentication authentication = authenticationRepository.findAuthenticationByToken(token);
        if (authentication == null){
            throw new WrongTokenException("Wrong token!");
        }
        User user = authentication.getUser();
        account.setUser(user);
        accountRepository.save(account);
        AccountDTO accountDTO = new AccountDTO(account.getAccount_Number(), account.getAccount_Type(),
                account.getBalance(), account.getCreatedTime(), account.getUpdatedTime());
        return accountDTO;
    }

}
