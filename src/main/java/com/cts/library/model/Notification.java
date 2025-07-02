package com.cts.library.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "notifications")
public class Notification {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationId;

    private String message;

    private Date dateSent;

    @ManyToOne
    private Member member;

    @ManyToOne
    private Book book;

    @ManyToOne
    private Fine fine;

    private long overdueDays;

    public Notification() {}

    public Notification(long overdueDays, Member member, Book book, Fine fine, String message, Date dateSent) {
        this.overdueDays = overdueDays;
        this.member = member;
        this.book = book;
        this.fine = fine;
        this.message = message;
        this.dateSent = dateSent;
    }
	
	public Long getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(Long notificationId) {
		this.notificationId = notificationId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getDateSent() {
		return dateSent;
	}

	public void setDateSent(Date dateSent) {
		this.dateSent = dateSent;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public Fine getFine() {
		return fine;
	}

	public void setFine(Fine fine) {
		this.fine = fine;
	}

	public long getOverdueDays() {
		return overdueDays;
	}

	public void setOverdueDays(long overdueDays) {
		this.overdueDays = overdueDays;
	}

	
}
