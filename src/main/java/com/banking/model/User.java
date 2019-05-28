package com.banking.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "user", schema = "bank-app")
public class User extends IdModel {

    @OneToMany(targetEntity = Account.class, mappedBy = "user", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private List<Account> accounts;

    @OneToMany(targetEntity = Notification.class, mappedBy = "user", cascade = CascadeType.MERGE,
            fetch = FetchType.LAZY)
    private List<Notification> notifications;

    @Column(name="username", length = 16, nullable = false)
    private String userName;

    @Column(name="password", length = 32, nullable = false)
    private String password;

    @Column(name = "created_time", length = 8)
    private LocalDateTime createdTime;

    @Column(name = "updated_time", length = 8)
    private LocalDateTime updatedTime;

    public User(){}

    public User(String userName, String password) {
        super();
        this.userName = userName;
        this.password = password;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(getAccounts(), user.getAccounts()) &&
                Objects.equals(getNotifications(), user.getNotifications()) &&
                Objects.equals(getUserName(), user.getUserName()) &&
                Objects.equals(getPassword(), user.getPassword()) &&
                Objects.equals(getCreatedTime(), user.getCreatedTime()) &&
                Objects.equals(getUpdatedTime(), user.getUpdatedTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAccounts(), getNotifications(), getUserName(), getPassword(), getCreatedTime(), getUpdatedTime());
    }

    @Override
    public String toString() {
        return "User{" +
                "accounts=" + accounts +
                ", notifications=" + notifications +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", createdTime=" + createdTime +
                ", updatedTime=" + updatedTime +
                '}';
    }

}
