package com.banking.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "authentication", schema = "bank-app")
public class Authentication extends IdModel {

    @Column(name = "token", length = 20)
    private String token;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="user_id")
    private User user;

    @Column(name = "creation_time")
    private LocalDateTime creationTime;

    public Authentication(){}

    public Authentication(String token, User user, LocalDateTime creationTime){
        this.token = token;
        this.user = user;
        this.creationTime = creationTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof Authentication)) return false;
        Authentication that = (Authentication) o;
        return Objects.equals(getToken(), that.getToken()) &&
                Objects.equals(getUser(), that.getUser()) &&
                Objects.equals(getCreationTime(), that.getCreationTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getToken(), getUser(), getCreationTime());
    }
}
