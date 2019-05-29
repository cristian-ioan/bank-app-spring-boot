package com.banking.service.impl;

import com.banking.dto.AccountDTO;
import com.banking.exception.BalanceException;
import com.banking.exception.DetailsAccountException;
import com.banking.exception.WrongTokenException;
import com.banking.model.*;
import com.banking.repository.AccountRepository;
import com.banking.repository.AuthenticationRepository;
import com.banking.service.AccountService;
import com.banking.util.DetailsBankAccount;
import com.banking.util.IbanGeneratorUtils;
import com.banking.validation.AccountValidator;
import com.banking.validation.PaymentValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service("accountService")
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class AccountServiceImpl implements AccountService {

    private PaymentValidator paymentValidator = new PaymentValidator();
    private AccountValidator accountValidator = new AccountValidator();
    private Account newAccount;
    private final static Logger LOG = Logger.getLogger(Logger.class.getName());

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
    public List<Account> findAccountsByUserId(long id){
        return accountRepository.findAccountsByUserId(id);
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
    public void createUserBankAccount(User user)  throws BalanceException {

        String iban = IbanGeneratorUtils.generateIban();
        LOG.info("Iban: " + iban);

        BigDecimal balanceAccount = accountValidator.validateBalance();

        String currencyTypeOfAccount = accountValidator.validateCurrencyType();

        LocalDateTime createdTime = LocalDateTime.now();

        newAccount = new Account(user, iban, currencyTypeOfAccount, balanceAccount, createdTime, createdTime);

        createAccount(newAccount);

        LOG.info("The bank account for " + user.getUserName() + " was successfully created!");

        newAccount = null;
    }

    @Override
    public void verifyPayment(List<Account> accounts) throws DetailsAccountException {

        int numberUserAccounts = accounts.size();
        boolean isConditionForPayment = false;

        if (numberUserAccounts == 0){
            LOG.warning( "You don't have any bank account." );
        } else {
            if (numberUserAccounts == 1) {
                LOG.warning( "You have one bank account. Please create another bank account of the same type: "
                        + accounts.get(0).getAccount_Type());
            } else {
                if (numberUserAccounts == 2) {
                    String currencyTypeOfFirstAccount = accounts.get(0).getAccount_Type();
                    String currencyTypeOfSecondAccount = accounts.get(1).getAccount_Type();
                    if (!currencyTypeOfFirstAccount.equals(currencyTypeOfSecondAccount)) {
                        LOG.warning("You do not have accounts of the same currency type. Create another one!");
                    } else {
                        isConditionForPayment = true;
                    }
                } else {
                    isConditionForPayment = true;
                }
            }
        }

        if (isConditionForPayment) {
            int optionFrom = 0;
            long indexOfFirstAccount;

            LOG.info( "All user bank accounts are:" );
            DetailsBankAccount.showDetailsUserBankAccount(accounts);

            optionFrom = paymentValidator.validateOptionFrom(numberUserAccounts);

            indexOfFirstAccount = accounts.get( optionFrom - 1 ).getId();
            String currencyFirstAccount = accounts.get( optionFrom - 1 ).getAccount_Type();

            boolean continuePayment = false;
            for (Account account : accounts) {
                if (!continuePayment){
                    if ((indexOfFirstAccount != account.getId()) && (currencyFirstAccount.equals(account.getAccount_Type()))) {
                        continuePayment = true;
                    } else {
                        continuePayment = false;
                    }
                }
            }

            if (continuePayment) {
                makeTransfer(accounts, optionFrom, indexOfFirstAccount, currencyFirstAccount, numberUserAccounts);
            } else {
                LOG.warning( "You have a single " + currencyFirstAccount + " account. In order to make transfers " +
                        "between accounts, you must create another account of the same currency type." );
            }
        }
    }

    @Override
    public void makeTransfer(List<Account> accounts, int optionFrom,long indexOfFirstAccount, String currencyFirstAccount,
                             int numberUserAccounts) {

        BigDecimal balanceFirstAccount = accounts.get(optionFrom - 1).getBalance();

        BigDecimal validateBalanceOfPayment = paymentValidator.validateBalanceAccount( balanceFirstAccount );

        BigDecimal balanceSecondAccount;
        long indexOfSecondAccount;
        int optionTo;
        boolean isConditionForPayment = false;
        do {
            optionTo = paymentValidator.validateNumberAccount(optionFrom, numberUserAccounts);
            balanceSecondAccount = accounts.get(optionTo - 1).getBalance();
            indexOfSecondAccount = accounts.get(optionTo -1).getId();
            String currencySecondAccount = accounts.get( optionTo - 1).getAccount_Type();
            if(currencyFirstAccount.equals( currencySecondAccount)){
                isConditionForPayment = true;
            } else {
                LOG.warning( "Accounts must have the same currency type." );
            }
        } while (!isConditionForPayment);

        BigDecimal newBalanceOfFirstAccount = balanceFirstAccount.subtract( validateBalanceOfPayment );
        BigDecimal newBalanceOfSecondAccount = validateBalanceOfPayment.add( balanceSecondAccount );

        LOG.info( "Type details of tran: " );
        String detailsTransaction = IOService.getInstance().readLine();
        LocalDateTime createdTime = LocalDateTime.now();

        Transaction transactionIncoming = new Transaction( accounts.get(optionTo - 1).getAccount_Number(),
                validateBalanceOfPayment, detailsTransaction, createdTime, String.valueOf(FieldType.INCOMING),
                accounts.get(optionTo - 1));
        Transaction transactionOutgoing = new Transaction(accounts.get(optionFrom - 1).getAccount_Number(),
                validateBalanceOfPayment, detailsTransaction, createdTime, String.valueOf(FieldType.OUTGOING),
                accounts.get(optionFrom - 1));

        LocalDateTime sentTime = LocalDateTime.now();
        String detailsNotification = "From: ".concat(accounts.get(optionFrom-1).getAccount_Number()).
                concat(" To: ").concat(accounts.get(optionTo-1).getAccount_Number()).concat(" amount: ").
                concat(String.valueOf(validateBalanceOfPayment)).concat(" -> ").concat(detailsTransaction);
        Notification notification = new Notification(accounts.get(optionTo - 1).getUser(), detailsNotification,
                createdTime, sentTime);

        updateAccountcreateTransaction(accounts, indexOfFirstAccount, indexOfSecondAccount,
                newBalanceOfFirstAccount, newBalanceOfSecondAccount, transactionIncoming, transactionOutgoing, notification);

    }

    @Override
    public void updateAccountcreateTransaction(List<Account> accounts, long indexFirstAccount, long secondAccount, BigDecimal balanceOfFirstAccount, BigDecimal balanceOfSecondAccount, Transaction tranIncoming, Transaction tranOutgoing, Notification notification) {

        TransactionServiceImpl transactionServiceImpl = new TransactionServiceImpl();
        NotificationServiceImpl notificationServiceImpl = new NotificationServiceImpl();

        for (Account account : accounts) {
            if (account.getId() == indexFirstAccount) {
                account.setBalance(balanceOfFirstAccount);
                updateAccount(account);
            }
            if (account.getId() == secondAccount) {
                account.setBalance(balanceOfSecondAccount);
                updateAccount(account);
            }
        }

        transactionServiceImpl.createTransaction(tranIncoming);
        transactionServiceImpl.createTransaction(tranOutgoing);

        notificationServiceImpl.createNotification(notification);
    }

}
