package com.cts.library.repository;

import com.cts.library.model.Notification;
import com.cts.library.model.Member;
import com.cts.library.model.Book;
import com.cts.library.model.Fine;

import org.springframework.data.jpa.repository.JpaRepository;
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

}
