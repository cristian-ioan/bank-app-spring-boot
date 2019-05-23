package com.banking.validation;

import com.banking.service.impl.IOService;

import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.logging.Logger;

public class PaymentValidator {

    private final static Logger LOG = Logger.getLogger(Logger.class.getName());

    /**
     * Validates the account number
     *
     * @param numberUserAccounts validates the account number that we enter from the console
     * @return the id of account
     */
    public int validateOptionFrom(int numberUserAccounts){
        boolean isBadOption = false;
        int optionFrom = 0;
        while (!isBadOption){
            try {
                LOG.info( "Type the number of account (from) - it must be between 1 and " +
                        numberUserAccounts + ": " );
                optionFrom = IOService.getInstance().readInteger();
                while (optionFrom > numberUserAccounts || optionFrom < 0){
                    LOG.info( "Type the number of account (from) - it must be between 1 and " +
                            numberUserAccounts + ": " );
                    optionFrom = IOService.getInstance().readInteger();
                }
                isBadOption = true;
            } catch (InputMismatchException e){
                LOG.warning( "Incorrect entry. Please input only integer." );
                isBadOption = false;
            }
        }
        return optionFrom;
    }

    /**
     * Validates if the value that you want to be transferred does not exceed the value available in the user account.
     */
    public BigDecimal validateBalanceAccount(BigDecimal balance){
        BigDecimal balanceFrom = null;
        boolean isBadOption = false;
        while (!isBadOption)    {
            try {
                LOG.info("Enter balance for account: ");
                balanceFrom = IOService.getInstance().readBigDecimal();
                while (balance.compareTo(balanceFrom) < 0){
                    LOG.info("The value entered is higher than the existing balance. Type a new balance: ");
                    balanceFrom = IOService.getInstance().readBigDecimal();
                }
                isBadOption = true;
            } catch (InputMismatchException e) {
                LOG.warning( "Incorrect entry. Please input only integer." );
                isBadOption = false;
            }
        }
        return balanceFrom;
    }

    /**
     * Validates if the account where you want to transfer the money is the same with the account from which
     * the transfer is made.
     */
    public int validateNumberAccount(int optionFrom, int numberUserAccounts){
        int optionTo = 0;
        boolean isBadOption = false;
        while (!isBadOption){
            try {
                LOG.info( "Type the number of account (to transfer money): " );
                optionTo = IOService.getInstance().readInteger();
                while (optionTo > numberUserAccounts || optionTo < 0){
                    LOG.info( "Type the number of account (from) - it must be between 1 and " +
                            numberUserAccounts + ": " );
                    optionTo = IOService.getInstance().readInteger();
                }
                if (optionFrom == optionTo){
                    LOG.warning( "You cannot make money transfers within the same account. Please type another " +
                            "number of account (to transfer money):" );
                } else {
                    isBadOption = true;
                }
            } catch (InputMismatchException e){
                LOG.warning( "Incorrect entry. Please input only integer." );
                isBadOption = false;
            }
        }
        return optionTo;
    }

}
