package com.cts.library.service;

import com.cts.library.model.Book;
import com.cts.library.model.BorrowingTransaction;
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

    private NotificationRepo notificationRepository;

    private BorrowingTransactionRepo borrowingTransactionRepo;
	
	public NotificationServiceImpl(NotificationRepo notificationRepository,
			BorrowingTransactionRepo borrowingTransactionRepo) {
		super();
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
    
    @Scheduled(cron = "0 0 8 * * ?") 
    public void generateDueAndOverdueNotifications() {
        LocalDate today = LocalDate.now();

        List<BorrowingTransaction> allTransactions = borrowingTransactionRepo.findAll(); 

        for (BorrowingTransaction tx : allTransactions) {
            if (tx.getStatus().equals("RETURNED")) {
                continue; 
            }

            Member member = tx.getMember();
            Book book = tx.getBook();
            LocalDate returnDate = tx.getReturnDate();

            long daysUntilDue = ChronoUnit.DAYS.between(today, returnDate);
            long daysOverdue = ChronoUnit.DAYS.between(returnDate, today);

            String message;
            boolean shouldNotify = false;

            if (daysUntilDue >= 0 && daysUntilDue <= 2) {
                message = "Reminder: Book '" + book.getBookName() + "' is due in " + daysUntilDue + " day(s). Please return it on time.";
                shouldNotify = true;
            } else if (daysOverdue > 0) {
                Fine fine = notificationRepository.findLatestUnpaidFineByMember(member); 
                message = "Overdue: Book '" + book.getBookName() + "' was due " + daysOverdue + " day(s) ago. Current fine: ₹" + fine.getAmount();
                shouldNotify = true;
            } else {
                continue;
            }

            if (shouldNotify) {
                Notification notification = new Notification(daysOverdue, member, book, null, message, new Date());
//                notification.setMember(member);
//                notification.setBook(book);
//                notification.setMessage(message);
//                notification.setDateSent(new Date());

                if (daysOverdue > 0) {
                    Fine fine = notificationRepository.findLatestUnpaidFineByMember(member);
                    notification.setFine(fine);
                }

                notificationRepository.save(notification);
            }
        }
    }

}
