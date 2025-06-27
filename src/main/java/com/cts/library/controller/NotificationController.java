package com.cts.library.controller;

import com.cts.library.model.Notification;
import com.cts.library.service.NotificationService;
import com.cts.library.service.NotificationServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
	
    private NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
		super();
		this.notificationService = notificationService;
	}

	@PostMapping
    public Notification createNotification(@RequestBody Notification notification) {
        return notificationService.createNotification(notification);
    }

    @GetMapping
    public List<Notification> getAllNotifications() {
        return notificationService.getAllNotifications();
    }

    @GetMapping("/{id}")
    public Notification getNotification(@PathVariable Long id) {
        return notificationService.getNotificationById(id);
    }
    
    @GetMapping("/trigger")
    public String triggerNotificationsManually() {
        ((NotificationServiceImpl) notificationService).generateDueAndOverdueNotifications();
        return "Notifications triggered.";
    }

}
