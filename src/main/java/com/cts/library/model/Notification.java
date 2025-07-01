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
    private long notificationId;

    @ManyToOne
    @JoinColumn(name = "memberId", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "bookId")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "fineId")
    private Fine fine;

    @NotBlank(message = "Message cannot be blank")
    private String message;

    @NotNull(message = "Date sent cannot be null")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateSent;

	public Notification(long notificationId, Member member, Book book, Fine fine,
			@NotBlank(message = "Message cannot be blank") String message,
			@NotNull(message = "Date sent cannot be null") Date dateSent) {
		super();
		this.notificationId = notificationId;
		this.member = member;
		this.book = book;
		this.fine = fine;
		this.message = message;
		this.dateSent = dateSent;
	}

	public long getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(long notificationId) {
		this.notificationId = notificationId;
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

	
}
