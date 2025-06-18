package com.cts.library.service;

import com.cts.library.model.BorrowingTransaction;
import java.util.List;

public interface BorrowingTransactionService {
    BorrowingTransaction borrowBook(Long memberId, Long bookId);
    BorrowingTransaction returnBook(Long transactionId);
    List<BorrowingTransaction> getTransactionsByMember(Long memberId);
    List<BorrowingTransaction> getAllTransactions();
}
