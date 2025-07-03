package com.cts.library.repository;

import com.cts.library.model.Notification;

import jakarta.transaction.Transactional;

import com.cts.library.model.Member;
import com.cts.library.model.Book;
import com.cts.library.model.Fine;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
public interface NotificationRepo extends JpaRepository<Notification, Long> {
    List<Notification> findByMemberAndBookAndDateSentBetween(
        Member member, Book book, Date start, Date end);

    List<Notification> findByDateSentAfter(Date date);
    
    @Query("SELECT f FROM Fine f WHERE f.member = :member AND f.fineStatus = 'UNPAID' ORDER BY f.transactionDate DESC")
    Fine findLatestUnpaidFineByMember(@Param("member") Member member);
    
    List<Notification> findByMemberAndFine(Member member, Fine fine);
    @Modifying
    @Transactional
    @Query(value = """
        INSERT INTO notifications (overdue_days,member_id , book_id, fine_id, message, date_sent)
        SELECT 
            DATEDIFF(CURRENT_DATE, bt.return_date) AS overdue_days,
            bt.member_id,
            bt.book_id,
            f.fine_id,
            CONCAT(
                'Overdue: "', b.book_name, '" is ',
                DATEDIFF(CURRENT_DATE, bt.return_date),
                ' day(s) overdue. Fine ₹', f.amount,
                ' (Fine ID: ', f.fine_id, ').'
            ) AS message,
            CURRENT_DATE
        FROM borrowing_transaction bt
        JOIN fine f ON f.transaction_id = bt.transaction_id
        JOIN book b ON bt.book_id = b.book_id
        WHERE bt.status = 'BORROWED'
          AND bt.return_date < CURRENT_DATE
          AND LOWER(f.fine_status) = 'pending'
          AND NOT EXISTS (
              SELECT 1 FROM notifications n
               WHERE n.fine_id = f.fine_id
          )
        """, nativeQuery = true)
    int insertOverdueNotifications();
    
    void deleteByMember_MemberId(long memberId);



}
