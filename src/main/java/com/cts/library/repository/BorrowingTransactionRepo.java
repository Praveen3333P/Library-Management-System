package com.cts.library.repository;

import com.cts.library.model.BorrowingTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BorrowingTransactionRepo extends JpaRepository<BorrowingTransaction, Long> {
    List<BorrowingTransaction> findByMemberId(Long memberId);
    boolean existsByBookIdAndStatus(Long bookId, BorrowingTransaction.Status status);
}

