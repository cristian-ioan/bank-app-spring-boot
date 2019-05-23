package com.banking.validation;

import com.banking.exception.BalanceException;
import com.banking.service.impl.IOService;

import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.logging.Logger;

public class AccountValidator {

    private final static Logger LOG = Logger.getLogger(Logger.class.getName());

    /**
     * Validates currency type of bank account.
     *
     * @return balance of account
     */
    public BigDecimal validateBalance() throws BalanceException {

        BigDecimal balanceOfBankAccount;
        try {
            LOG.info("Enter balance for account: ");
            balanceOfBankAccount = IOService.getInstance().readBigDecimal();
        } catch (NumberFormatException err){
            throw new BalanceException( "Incorrect entry. Please input only integer.", err);
        }
        return balanceOfBankAccount;

    }

    /**
     * Validates currency type of bank account.
     *
     * @return currencyTypeOfUserBankAccount the currency type of account
     */
    public String validateCurrencyType(){

        String currencyType = null;
        int option;
        boolean isBadOption = false;

        do{
            try {
                LOG.info("Input 1 for RON or input 2 for EUR: ");
                option = IOService.getInstance().readInteger();

                switch (option) {
                    case 1:
                        currencyType = "RON";
                        isBadOption = true;
                        break;
                    case 2:
                        currencyType = "EUR";
                        isBadOption = true;
                        break;
                    default:
                        LOG.warning("Not a valid option.");
                        break;
                }
            } catch (InputMismatchException e){
                LOG.warning( "Incorrect entry. Please input 1 for RON or 2 for EUR." );
            }

        } while (!isBadOption);

        return currencyType;
    }

}
