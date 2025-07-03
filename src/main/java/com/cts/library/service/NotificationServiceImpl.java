package com.cts.library.service;

import com.cts.library.model.Book;
import com.cts.library.model.BorrowingTransaction;
import com.cts.library.model.BorrowingTransaction.Status;
import com.cts.library.model.Fine;
import com.cts.library.model.Member;
import com.cts.library.model.Notification;
import com.cts.library.repository.BorrowingTransactionRepo;
import com.cts.library.repository.NotificationRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepo notificationRepository;
    private final BorrowingTransactionRepo borrowingTransactionRepo;

    public NotificationServiceImpl(NotificationRepo notificationRepository,
                                   BorrowingTransactionRepo borrowingTransactionRepo) {
        this.notificationRepository = notificationRepository;
        this.borrowingTransactionRepo = borrowingTransactionRepo;
    }

    @Override
    public Notification createNotification(Notification notification) {
        notification.setDateSent(new Date());
        return notificationRepository.save(notification);
    }

    @Override
    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    @Override
    public Notification getNotificationById(Long id) {
        return notificationRepository.findById(id).orElse(null);
    }
    
	@Scheduled(cron = "00 43 18 * * *")
	public void generateDueAndOverdueNotifications() {


		int insertedrem = notificationRepository.insertDueReminders();
		int insertupdrem = notificationRepository.updateDueReminderMessages();
		int insertupdreturn = notificationRepository.updateReminderToDueTodayMessage();
		int inserteddue = notificationRepository.upgradeReminderToOverdue();
		int insertr = notificationRepository.upgradeOverdueToReturned();
		System.out.println("Inserted rows Due: " + inserteddue);
		System.out.println("Inserted rows Due: " + insertupdrem);
		System.out.println("Inserted rows Due: " + insertupdreturn);
		System.out.println("Inserted rows Rem: " + insertedrem);
		System.out.println("Inserted rows Returned: " + insertr);
	}


    

}
