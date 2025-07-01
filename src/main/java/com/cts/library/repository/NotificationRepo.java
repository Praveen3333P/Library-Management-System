package com.cts.library.repository;

import com.cts.library.model.Notification;
import com.cts.library.model.Member;
import com.cts.library.model.Book;
import com.cts.library.model.Fine;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface NotificationRepo extends JpaRepository<Notification, Long> {
    List<Notification> findByMemberAndBookAndDateSentBetween(
        Member member, Book book, Date start, Date end);

    List<Notification> findByDateSentAfter(Date date);
    
    @Query("SELECT f FROM Fine f WHERE f.member = :member AND f.status = 'Pending' ORDER BY f.transactionDate DESC")
    Fine findLatestUnpaidFineByMember(@Param("member") Member member);

}
