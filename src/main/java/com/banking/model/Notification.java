package com.banking.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "notification", schema = "bank-app")
public class Notification extends IdModel {

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name="details", length = 200)
    private String details;

    @Column(name = "created_time", length = 8)
    private LocalDateTime createdTime;

    @Column(name = "sent_time", length = 8)
    private LocalDateTime sentTime;

    public Notification(User user, String details, LocalDateTime createdTime, LocalDateTime sentTime){
        this.user = user;
        this.details = details;
        this.createdTime = createdTime;
        this.sentTime = sentTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public LocalDateTime getSentTime() {
        return sentTime;
    }

    public void setSentTime(LocalDateTime sentTime) {
        this.sentTime = sentTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof Notification)) return false;
        Notification that = (Notification) o;
        return Objects.equals(getUser(), that.getUser()) &&
                Objects.equals(getDetails(), that.getDetails()) &&
                Objects.equals(getCreatedTime(), that.getCreatedTime()) &&
                Objects.equals(getSentTime(), that.getSentTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUser(), getDetails(), getCreatedTime(), getSentTime());
    }

    @Override
    public String toString() {
        return "Notification{" +
                "user=" + user +
                ", details='" + details + '\'' +
                ", createdTime=" + createdTime +
                ", sentTime=" + sentTime +
                '}';
    }
}
