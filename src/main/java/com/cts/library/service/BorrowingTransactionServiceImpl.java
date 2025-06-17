package com.cts.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.library.model.BorrowingTransaction;
import com.cts.library.repository.BorrowingTransactionRepo;


@Service
public class BorrowingTransactionServiceImpl {
	
	@Autowired
	private BorrowingTransactionRepo borrowingTransactionRepo;
	
	public BorrowingTransaction saveBorrowingTransaction(BorrowingTransaction borrowingTransaction) {
		return borrowingTransactionRepo.save(borrowingTransaction);
	}
}
