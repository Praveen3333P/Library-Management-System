package com.cts.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.library.model.BorrowingTransaction;

@Repository
public interface BorrowingTransactionRepo extends JpaRepository<BorrowingTransaction, Long>{

}
