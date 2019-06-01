package com.banking.dto;

import java.math.BigDecimal;

public class TransferDTO {
    private String token;
    private BigDecimal amount;
    private String details;
    private String fromAccount;
    private String toAccount;

    public TransferDTO(String token, BigDecimal amount, String details, String fromAccount, String toAccount) {
        super();
        this.token = token;
        this.amount = amount;
        this.details = details;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(String fromAccount) {
        this.fromAccount = fromAccount;
    }

    public String getToAccount() {
        return toAccount;
    }

    public void setToAccount(String toAccount) {
        this.toAccount = toAccount;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

}
