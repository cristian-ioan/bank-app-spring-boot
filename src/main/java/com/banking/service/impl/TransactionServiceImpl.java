package com.banking.service.impl;

import com.banking.dto.TransactionDTO;
import com.banking.dto.TransferDTO;
import com.banking.exception.WrongTokenException;
import com.banking.model.*;
import com.banking.repository.AccountRepository;
import com.banking.repository.AuthenticationRepository;
import com.banking.repository.NotificationRepository;
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
@Transactional(rollbackFor = Exception.class)
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AuthenticationRepository authenticationRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private NotificationRepository notificationRepository;

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
    public List<TransactionDTO> transferMoneyByToken(TransferDTO transferDTO) throws WrongTokenException {
        String token = transferDTO.getToken();
        Authentication authentication = authenticationRepository.findAuthenticationByToken(token);
        if (authentication == null){
            throw new WrongTokenException("Wrong token!");
        }
        List<TransactionDTO> transactionsDTOList = new ArrayList<>();
        AccountServiceImpl accountServiceImpl = new AccountServiceImpl();
        User user = authentication.getUser();
        LocalDateTime createdTime = LocalDateTime.now();
        List<Account> accountList = accountRepository.findAccountsByUserId(user.getId());
        Account accountFrom = accountServiceImpl.findAccountNumber(accountList, transferDTO.getFromAccount()).get();
        BigDecimal newBalanceOfFirstAccount = accountFrom.getBalance().subtract(transferDTO.getAmount());
        accountFrom.setBalance(newBalanceOfFirstAccount);
        Transaction transactionFrom = new Transaction(transferDTO.getFromAccount(), transferDTO.getAmount(),
                        transferDTO.getDetails(), createdTime, "OUTGOING",accountFrom);
        transactionRepository.save(transactionFrom);
        accountRepository.save(accountFrom);

        Account accountTo = accountServiceImpl.findAccountNumber(accountList, transferDTO.getToAccount()).get();
        BigDecimal newBalanceOfSecondAccount = accountTo.getBalance().add(transferDTO.getAmount());
        accountTo.setBalance(newBalanceOfSecondAccount);
        Transaction transactionTo = new Transaction(transferDTO.getToAccount(), transferDTO.getAmount(),
                transferDTO.getDetails(), createdTime, "INCOMING",accountTo);
        transactionRepository.save(transactionTo);
        accountRepository.save(accountTo);

        transactionsDTOList.add(new TransactionDTO(transactionFrom.getAccountNumber(),
                        transactionFrom.getAmount(), transactionFrom.getDetail(), transactionFrom.getCreatedTime(),
                        transactionFrom.getFieldType()));
        transactionsDTOList.add(new TransactionDTO(transactionTo.getAccountNumber(),
                        transactionTo.getAmount(), transactionTo.getDetail(), transactionTo.getCreatedTime(),
                        transactionTo.getFieldType()));

        LocalDateTime sentTime = LocalDateTime.now();
        String detailsNotification = "From: ".concat(accountFrom.getAccount_Number()).
                concat(" To: ").concat(accountTo.getAccount_Number()).concat(" amount: ").
                concat(String.valueOf(transferDTO.getAmount())).concat(" -> ").concat(transferDTO.getDetails());
        Notification notification = new Notification(user, detailsNotification, createdTime, sentTime, false);
        notificationRepository.save(notification);

        return transactionsDTOList;
    }

}
