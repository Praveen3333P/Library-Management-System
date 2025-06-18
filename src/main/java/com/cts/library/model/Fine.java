package com.cts.library.model;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
public class Fine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fineId;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    private double amount;

    private String status;

    private LocalDate transactionDate;

    public Fine() {
    }

    public Fine(Long fineId, Member member, double amount, String status, LocalDate transactionDate) {
        this.fineId = fineId;
        this.member = member;
        this.amount = amount;
        this.status = status;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }
}
