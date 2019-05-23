package com.banking.service.impl;

import com.banking.model.Transaction;
import com.banking.repository.TransactionRepository;
import com.banking.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("transactionService")
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

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
}
