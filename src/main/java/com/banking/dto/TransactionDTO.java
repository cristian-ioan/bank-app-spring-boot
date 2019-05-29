package com.banking.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionDTO {

    private String accountNumber;
    private BigDecimal amount;
    private String detail;
    private LocalDateTime createdTime;
    private String fieldType;

    public TransactionDTO(String accountNumber, BigDecimal amount, String detail, LocalDateTime createdTime,
                          String fieldType){
        super();
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.detail = detail;
        this.createdTime = createdTime;
        this.fieldType = fieldType;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }
}
