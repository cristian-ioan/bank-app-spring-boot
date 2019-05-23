package com.banking.repository;

import com.banking.model.Notification;
import com.banking.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends GenericRepository<Notification> {
    List<Notification> findNotificationsByUser(User user);
}
