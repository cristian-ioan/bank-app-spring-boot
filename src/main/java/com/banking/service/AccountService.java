package com.banking.service;

import com.banking.dto.AccountDTO;
import com.banking.exception.BalanceException;
import com.banking.exception.DetailsAccountException;
import com.banking.exception.WrongTokenException;
import com.banking.model.Account;
import com.banking.model.Notification;
import com.banking.model.Transaction;
import com.banking.model.User;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public interface AccountService {
    Account findById(Long id);
    List<Account> findAll();
    Account createAccount(Account account);
    Account updateAccount(Account account);
    void deleteAccount(Account account);

    List<Account> findAccountsByUserId(long id);

    void createUserBankAccount(User user) throws BalanceException;
    void verifyPayment(List<Account> accounts) throws DetailsAccountException;
    void makeTransfer(List<Account> accounts, int optionFrom,long indexOfFirstAccount, String currencyFirstAccount,
                      int numberUserAccounts);
    void updateAccountcreateTransaction(List<Account> accounts, long indexFirstAccount, long secondAccount,
                                        BigDecimal balanceOfFirstAccount, BigDecimal balanceOfSecondAccount,
                                        Transaction tranIncoming, Transaction tranOutgoing,
                                        Notification notification);

    List<AccountDTO> getAccountsByToken(String token) throws WrongTokenException;
}
