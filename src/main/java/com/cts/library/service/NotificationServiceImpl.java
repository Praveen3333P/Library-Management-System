package com.cts.library.service;

import com.cts.library.model.Notification;
import com.cts.library.repository.NotificationRepo;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepo notificationRepository;

    private final JavaMailSender mailSender;

    public NotificationServiceImpl(NotificationRepo notificationRepository,
                                   JavaMailSender mailSender) {
        this.notificationRepository = notificationRepository;
        this.mailSender = mailSender;
    }

    @Override
    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    @Override
    public Notification getNotificationById(Long id) {
        return notificationRepository.findById(id).orElse(null);
    }
    
	@Scheduled(cron = "00 45 10 * * *")
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
		debugNotificationFlow();
		
		
		
		
		 List<Notification> todayNotifications = notificationRepository.findByDateSentAfter(
			        java.sql.Date.valueOf(java.time.LocalDate.now().minusDays(1))
			    );

		 for (Notification notification : todayNotifications) {
			    String toEmail = (notification.getMember() != null) ? notification.getMember().getEmail() : "null";
			    System.out.println("Preparing to send to member ID: " + notification.getMember().getMemberId() + ", email: " + toEmail);
			

		        
		        
		        
		        String fullMessage = notification.getMessage();
		        String subject;
		        if (fullMessage.startsWith("Urgent Reminder:")) {
		            subject = "Urgent Reminder:";
		        } else if (fullMessage.startsWith("Reminder:")) {
		            subject = "Reminder:";
		        } else if (fullMessage.startsWith("Overdue:")) {
		            subject = "Overdue:";
		        } else if (fullMessage.startsWith("Fine Paid & Book Returned:")) {
		            subject = "Fine Paid & Book Returned:";
		        } else {
		            subject = "Library Notification";
		        }


		        SimpleMailMessage sms = new SimpleMailMessage();
		        sms.setTo(toEmail);
		        sms.setSubject(subject);
		        sms.setText(fullMessage);
		        mailSender.send(sms);
		        
		    }
		
		
	}
	public void debugNotificationFlow() {
	    List<Notification> test = notificationRepository.findByDateSentAfter(
	        java.sql.Date.valueOf(java.time.LocalDate.now().minusDays(1))
	    );

	    System.out.println("Notifications found: " + test.size());
	    for (Notification n : test) {
	        System.out.println("Notification: " + n.getMessage());
	    }
	}

}
