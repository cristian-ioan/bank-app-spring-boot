package com.banking.dto.converter;

import com.banking.dto.AccountDTO;
import com.banking.model.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountConverter {

    public AccountDTO convertToAccountDTO(Account account){
        return new AccountDTO(account.getAccount_Number(), account.getAccount_Type(), account.getBalance(),
                account.getCreatedTime(), account.getUpdatedTime());
    }
}
