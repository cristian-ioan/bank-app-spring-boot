package com.banking.service.impl;

import com.banking.model.Notification;
import com.banking.repository.NotificationRepository;
import com.banking.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("notificationService")
@Transactional(rollbackFor = Exception.class)
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public Notification findById(Long id) {
        return notificationRepository.findById(id);
    }

    @Override
    public List<Notification> findAll() {
        return notificationRepository.findAll();
    }

    @Override
    @Transactional
    public Notification createNotification(Notification notification) {
        return notificationRepository.save(notification);
    }

    @Override
    @Transactional
    public Notification updateNotification(Notification notification) {
        return notificationRepository.save(notification);
    }

    @Override
    @Transactional
    public void deleteNotification(Notification notification) {
        notificationRepository.delete(notification);
    }
}
