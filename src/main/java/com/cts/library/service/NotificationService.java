package com.cts.library.service;

import com.cts.library.model.BorrowingTransaction;
import com.cts.library.model.Fine;
import com.cts.library.model.Notification;
import java.util.List;

public interface NotificationService {
    public Notification createNotification(Notification notification);
    public List<Notification> getAllNotifications();
    public Notification getNotificationById(Long id);
    public void generateDueAndOverdueNotifications(); 
}