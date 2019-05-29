package com.banking.service;

import com.banking.dto.TransactionDTO;
import com.banking.exception.WrongTokenException;
import com.banking.model.Transaction;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public interface TransactionService {

    Transaction findById(Long id);
    List<Transaction> findAll();
    Transaction createTransaction(Transaction transaction);
    Transaction updateTransaction(Transaction transaction);
    void deleteTransaction(Transaction transaction);

    List<TransactionDTO> getTransactionsByToken(String token) throws WrongTokenException;

    List<TransactionDTO> transferMoneyByToken(String token, BigDecimal amount, String fromAccount, String toAccount,
                                        Transaction transaction) throws WrongTokenException;
}
