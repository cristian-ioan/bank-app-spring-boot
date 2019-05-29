package com.banking.dto.converter;

import com.banking.dto.TransactionDTO;
import com.banking.model.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionConverter {

    public TransactionDTO convertToTransactionDTO(Transaction transaction){
        return new TransactionDTO(transaction.getAccountNumber(), transaction.getAmount(), transaction.getDetail(),
                transaction.getCreatedTime(), transaction.getFieldType());
    }
}
