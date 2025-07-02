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

//    @Scheduled(cron = "0 46 17 * * *")
//    public void generateDueAndOverdueNotifications() {
//        LocalDate today = LocalDate.now();
//        List<BorrowingTransaction> allTransactions = borrowingTransactionRepo.findAll();
//
//        for (BorrowingTransaction tx : allTransactions) {
//            Member member = tx.getMember();
//            Book book = tx.getBook();
//            LocalDate returnDate = tx.getReturnDate();
//
//            if (tx.getStatus().name().equalsIgnoreCase("BORROWED")) {
//                long daysUntilDue = ChronoUnit.DAYS.between(today, returnDate);
//                long daysOverdue = ChronoUnit.DAYS.between(returnDate, today);
//
//                if (daysUntilDue == 1) {
//                    Notification reminder = new Notification(
//                        0, member, book, null,
//                        " Reminder: \"" + book.getBookName() + "\" is due in 1  days (" + returnDate + "). Please return it on time.",
//                        new Date()
//                    );
//                    notificationRepository.save(reminder);
//                } else if (daysOverdue > 0) {
//                    Fine fine = notificationRepository.findLatestUnpaidFineByMember(member);
//                    if (fine != null && fine.getFineStatus().equalsIgnoreCase("UNPAID")) {
//                        List<Notification> existingNotifications = notificationRepository.findByMemberAndFine(member, fine);
//
//                        if (existingNotifications.isEmpty()) {
//                            Notification overdue = new Notification(
//                                daysOverdue,
//                                member,
//                                book,
//                                fine,
//                                " Overdue: \"" + book.getBookName() + "\" is " + daysOverdue +
//                                " day(s) overdue. Fine ₹" + fine.getAmount() + " (Fine ID: " + fine.getFineId() + ").",
//                                new Date()
//                            );
//                            notificationRepository.save(overdue);
//                        } else {
//                            Notification latest = existingNotifications.get(0);
//                            latest.setOverdueDays(daysOverdue);
//                            latest.setMessage(" Overdue: \"" + book.getBookName() + "\" is " + daysOverdue +
//                                              " day(s) overdue. Fine ₹" + fine.getAmount() + " (Fine ID: " + fine.getFineId() + ").");
//                            latest.setDateSent(new Date());
//                            notificationRepository.save(latest);
//                        }
//                    }
//                }
//
//            } else if (tx.getStatus().name().equalsIgnoreCase("RETURNED")) {
//                notifyBookReturned(tx);
//            }
//        }
//    }
    
	@Scheduled(cron = "0 26 18 * * *")
	public void generateDueAndOverdueNotifications() {

		notificationRepository.insertOverdueNotifications();
	}

    @Override
    public void notifyBookReturned(BorrowingTransaction tx) {
        Member member = tx.getMember();
        Book book = tx.getBook();

        Notification returnedNotification = new Notification(
            0,
            member,
            book,
            null,
            " Book Returned: \"" + book.getBookName() + "\" successfully returned. Thank you, " + member.getName() + "!",
            new Date()
        );

        notificationRepository.save(returnedNotification);
    }

    // implement this method in fineserviceimplementation for notification if paid 
//    @Override
//    public void notifyFinePaid(Fine fine) {
//        Member member = fine.getMember();
//
//        Notification finePaidNotification = new Notification(
//            0,
//            member,
//            null,
//            fine,
//            " Fine Paid: " + fine.getAmount() + " has been paid (Fine ID: " + fine.getFineId() +
//            "). You're all cleared, " + member.getName() + "!",
//            new Date()
//        );
//
//        notificationRepository.save(finePaidNotification);
//    }
    
    
//     notificationService.notifyFinePaid(updatedFine);
}
