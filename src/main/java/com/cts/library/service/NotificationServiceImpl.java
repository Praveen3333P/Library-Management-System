package com.cts.library.service;

import com.cts.library.model.Notification;
import com.cts.library.repository.NotificationRepo;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepo notificationRepository;


    public NotificationServiceImpl(NotificationRepo notificationRepository) {
        this.notificationRepository = notificationRepository;

    }

    @Override
    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    @Override
    public Notification getNotificationById(Long id) {
        return notificationRepository.findById(id).orElse(null);
    }
    
	@Scheduled(cron = "00 00 09 * * *")
	public void generateDueAndOverdueNotifications() {


		int insertRemainder = notificationRepository.insertDueReminders();
		int updateRemainder = notificationRepository.updateDueReminderMessages();
		int updatetoReturnToday = notificationRepository.updateReminderToDueTodayMessage();
		int updateRemaindertoOverdue = notificationRepository.upgradeReminderToOverdue();
		int updateOverduetoReturned = notificationRepository.upgradeOverdueToReturned();
		System.out.println("Insert Remainder into Notifications : " + insertRemainder);
		System.out.println("Update Notifications as per Return Day Varies : " + updateRemainder);
		System.out.println("Update Notfifcations on Return Day: " + updatetoReturnToday);
		System.out.println("Update Notifications from Remainder to Overdue : " + updateRemaindertoOverdue);
		System.out.println("Update Notifications from Overdue to Returned : " + updateOverduetoReturned);
	}


    

}
