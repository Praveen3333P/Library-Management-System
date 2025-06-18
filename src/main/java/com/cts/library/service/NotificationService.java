package com.cts.library.service;

import com.cts.library.model.Notification;
import java.util.List;

public interface NotificationService {
    Notification createNotification(Notification notification);
    List<Notification> getAllNotifications();
    Notification getNotificationById(Long id);
    public void generateDueAndOverdueNotifications();
}
