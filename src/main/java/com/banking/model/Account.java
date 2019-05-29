package com.banking.model;

import com.banking.validation.CurrencyType;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "account", schema = "bank-app")
public class Account extends IdModel {

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(targetEntity = Transaction.class, mappedBy = "account", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private List<Transaction> transactions;

    @Column(name="account_number", length = 50)
    private String account_Number;

    @CurrencyType
    @NotEmpty(message = "Please provide another currency type!")
    @Column(name="account_type", length = 50)
    private String account_Type;

    @Column(name="balance", precision = 16, scale = 2)
    private BigDecimal balance;

    @Column(name = "created_time", length = 8)
    private LocalDateTime createdTime;

    @Column(name = "updated_time", length = 8)
    private LocalDateTime updatedTime;

    public Account(){};

    public Account(User user, String account_Number, String account_Type,
                   BigDecimal balance, LocalDateTime createdTime, LocalDateTime updatedTime) {
        this.user = user;
        this.account_Number = account_Number;
        this.account_Type = account_Type;
        this.balance = balance;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof Account)) return false;
        Account account = (Account) o;
        return Objects.equals(getUser(), account.getUser()) &&
                Objects.equals(getTransactions(), account.getTransactions()) &&
                Objects.equals(getAccount_Number(), account.getAccount_Number()) &&
                Objects.equals(getAccount_Type(), account.getAccount_Type()) &&
                Objects.equals(getBalance(), account.getBalance()) &&
                Objects.equals(getCreatedTime(), account.getCreatedTime()) &&
                Objects.equals(getUpdatedTime(), account.getUpdatedTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUser(), getTransactions(), getAccount_Number(), getAccount_Type(), getBalance(), getCreatedTime(), getUpdatedTime());
    }

}
