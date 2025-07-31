package com.cts.library.model;

import com.cts.library.model.BorrowingTransaction.Status;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "admin_requests")
public class AdminRequests {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long requestId;

    private Long memberId;
    private String memberName;

    private Long bookId;
    private String bookName;

    @Enumerated(EnumType.STRING)
    private BorrowingTransaction.Status status;
    
    public AdminRequests() {
    }

	public AdminRequests(Long requestId, Long memberId, String memberName, Long bookId, String bookName,
			Status status) {
		super();
		this.requestId = requestId;
		this.memberId = memberId;
		this.memberName = memberName;
		this.bookId = bookId;
		this.bookName = bookName;
		this.status = status;
	}

	public Long getRequestId() {
		return requestId;
	}

	public void setRequestId(Long requestId) {
		this.requestId = requestId;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public BorrowingTransaction.Status getStatus() {
		return status;
	}

	public void setStatus(BorrowingTransaction.Status status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Admin_Requests [requestId=" + requestId + ", memberId=" + memberId + ", memberName=" + memberName
				+ ", bookId=" + bookId + ", bookName=" + bookName + ", status=" + status + "]";
	}

  
    
    
}
