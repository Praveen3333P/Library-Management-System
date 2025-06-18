package com.cts.library.service;

import java.util.List;

import com.cts.library.model.BorrowingTransaction;

public interface BorrowingTransactionService {
	public BorrowingTransaction saveBorrowingTransaction(BorrowingTransaction borrowingTransaction);
	List<BorrowingTransaction> getTransactionsByMember(Long memberId);
    List<BorrowingTransaction> getAllTransactions();
}
