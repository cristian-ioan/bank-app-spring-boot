package com.banking.service;

import com.banking.model.Notification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NotificationService {

    Notification findById(Long id);
    List<Notification> findAll();
    Notification createNotification(Notification notification);
    Notification updateNotification(Notification notification);
    void deleteNotification(Notification notification);

}
