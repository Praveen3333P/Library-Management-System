package com.cts.library.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;

@Entity
public class Fine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fineId;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    @JsonBackReference
    private Member member;

    @ManyToOne
    @JoinColumn(name = "transaction_id", nullable = false)
    private BorrowingTransaction transaction;

    public BorrowingTransaction getTransaction() {
		return transaction;
	}

	public void setTransaction(BorrowingTransaction transaction) {
		this.transaction = transaction;
	}

	private double amount;

    private String fineStatus;

    private LocalDate transactionDate;

    public Fine() {
    }

    public Fine(Long fineId, Member member, double amount, String finestatus, LocalDate transactionDate) {
        this.fineId = fineId;
        this.member = member;
        this.amount = amount;
        this.fineStatus = finestatus;
        this.transactionDate = transactionDate;
    }

    public Long getFineId() {
        return fineId;
    }

    public void setFineId(Long fineId) {
        this.fineId = fineId;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getFineStatus() {
        return fineStatus;
    }

    public void setFineStatus(String finestatus) {
        this.fineStatus = finestatus;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }
}
