package com.cts.library.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Fine {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long fineId;
	private Member member;
	private double amount;
	private String status;
	private LocalDate transactionDate;
	
	
}
