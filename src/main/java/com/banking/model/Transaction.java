package com.banking.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "transaction", schema = "bank-app")
public class Transaction extends IdModel {

    @Column(name="account", length = 50, nullable = false)
    private String accountNumber;

    @Column(name="amount", precision = 16, scale = 2)
    private BigDecimal amount;

    @Column(name="details", length = 100)
    private String detail;

    @Column(name = "created_time", length = 8)
    private LocalDateTime createdTime;

    @Column(name = "field_type")
    private String fieldType;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name="account_id")
    private Account account;

    public Transaction(){}

    public Transaction(String accountNumber, BigDecimal amount, String detail, LocalDateTime createdTime,
                       String fieldType, Account account){
        this.accountNumber= accountNumber;
        this.amount = amount;
        this.detail = detail;
        this.createdTime = createdTime;
        this.fieldType = fieldType;
        this.account = account;
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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof Transaction)) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(getAccountNumber(), that.getAccountNumber()) &&
                Objects.equals(getAmount(), that.getAmount()) &&
                Objects.equals(getDetail(), that.getDetail()) &&
                Objects.equals(getCreatedTime(), that.getCreatedTime()) &&
                Objects.equals(getFieldType(), that.getFieldType()) &&
                Objects.equals(getAccount(), that.getAccount());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAccountNumber(), getAmount(), getDetail(), getCreatedTime(), getFieldType(), getAccount());
    }
}
