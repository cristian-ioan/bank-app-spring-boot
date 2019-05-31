package com.banking.service.impl;

import com.banking.dto.TransactionDTO;
import com.banking.exception.WrongTokenException;
import com.banking.model.Account;
import com.banking.model.Authentication;
import com.banking.model.Transaction;
import com.banking.model.User;
import com.banking.repository.AccountRepository;
import com.banking.repository.AuthenticationRepository;
import com.banking.repository.TransactionRepository;
import com.banking.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service("transactionService")
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AuthenticationRepository authenticationRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Transaction findById(Long id) {
        return transactionRepository.findById(id);
    }

    @Override
    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    @Override
    @Transactional
    public Transaction createTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    @Transactional
    public Transaction updateTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    @Transactional
    public void deleteTransaction(Transaction transaction) {
        transactionRepository.delete(transaction);
    }

    @Override
    public List<TransactionDTO> getTransactionsByToken(String token) throws WrongTokenException {
        Authentication authentication = authenticationRepository.findAuthenticationByToken(token);
        if (authentication == null){
            throw new WrongTokenException("Wrong token!");
        }
        User user = authentication.getUser();
        List<Account> accountList = accountRepository.findAccountsByUserId(user.getId());
        List<Transaction> accountTransactionsList = transactionRepository.findAll();
        List<TransactionDTO> transactionsDTOList = new ArrayList<>();
        for (Transaction transaction : accountTransactionsList){
            for (Account account : accountList){
                if (transaction.getAccount().equals(account)){
                    transactionsDTOList.add(new TransactionDTO(transaction.getAccountNumber(),
                            transaction.getAmount(), transaction.getDetail(), transaction.getCreatedTime(),
                            transaction.getFieldType()));
                }
            }
        }
        return transactionsDTOList;
    }

    @Override
    public List<TransactionDTO> transferMoneyByToken(String token, BigDecimal amount, String fromAccount,
                                               String toAccount, Transaction transaction) throws WrongTokenException {
        Authentication authentication = authenticationRepository.findAuthenticationByToken(token);

        if (authentication == null){
            throw new WrongTokenException("Wrong token!");
        }
        User user = authentication.getUser();
        List<Account> accountList = accountRepository.findAccountsByUserId(user.getId());
        List<TransactionDTO> transactionsDTOList = new ArrayList<>();
        for(Account account : accountList){
            if (account.getAccount_Number().equals(fromAccount)){
                LocalDateTime createdTime = LocalDateTime.now();
                BigDecimal newBalanceOfFirstAccount = account.getBalance().subtract(amount);
                account.setBalance(newBalanceOfFirstAccount);
                transaction.setAccountNumber(fromAccount);
                transaction.setAmount(amount);
                transaction.setCreatedTime(createdTime);
                transaction.setFieldType("OUTGOING");
                transaction.setAccount(account);
                transactionRepository.save(transaction);
                accountRepository.save(account);
                transactionsDTOList.add(new TransactionDTO(transaction.getAccountNumber(),
                        transaction.getAmount(), transaction.getDetail(), transaction.getCreatedTime(),
                        transaction.getFieldType()));
            }
        }
//        for(Account account : accountList){
//            if (account.getAccount_Number().equals(toAccount)){
//                LocalDateTime createdTime = LocalDateTime.now();
//                BigDecimal newBalanceOfSecondAccount = account.getBalance().add(amount);
//                account.setBalance(newBalanceOfSecondAccount);
//                transaction.setAccountNumber(toAccount);
//                transaction.setAmount(amount);
//                transaction.setCreatedTime(createdTime);
//                transaction.setFieldType("INCOMING");
//                transaction.setAccount(account);
//                transactionRepository.save(transaction);
//                accountRepository.save(account);
//                transactionsDTOList.add(new TransactionDTO(transaction.getAccountNumber(),
//                        transaction.getAmount(), transaction.getDetail(), transaction.getCreatedTime(),
//                        transaction.getFieldType()));
//            }
//        }
        return transactionsDTOList;
    }

}
