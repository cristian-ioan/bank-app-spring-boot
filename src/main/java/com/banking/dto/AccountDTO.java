package com.banking.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AccountDTO {

    private String account_Number;
    private String account_Type;
    private BigDecimal balance;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;

    public AccountDTO(String account_Number, String account_Type, BigDecimal balance, LocalDateTime createdTime,
                      LocalDateTime updatedTime){
        super();
        this.account_Number = account_Number;
        this.account_Type = account_Type;
        this.balance = balance;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
    }

    public String getAccount_Number() {
        return account_Number;
    }

    public void setAccount_Number(String account_Number) {
        this.account_Number = account_Number;
    }

    public String getAccount_Type() {
        return account_Type;
    }

    public void setAccount_Type(String account_Type) {
        this.account_Type = account_Type;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public LocalDateTime getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(LocalDateTime updatedTime) {
        this.updatedTime = updatedTime;
    }
}
