package com.cts.library.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.library.model.BorrowingTransaction;

@Repository
public interface BorrowingTransactionRepo extends JpaRepository<BorrowingTransaction, Long>{
	List<BorrowingTransaction> findByMemberId(Long memberId);
    List<BorrowingTransaction> findByBookId(Long bookId);
    List<BorrowingTransaction> findByStatus(String status);
}
