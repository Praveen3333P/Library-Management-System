package com.cts.library.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.library.model.BorrowingTransaction;
import com.cts.library.repository.BorrowingTransactionRepo;


@Service
public class BorrowingTransactionServiceImpl implements BorrowingTransactionService {

    @Autowired
    private BorrowingTransactionRepo borrowingTransactionRepo;

    @Override
    public BorrowingTransaction saveBorrowingTransaction(BorrowingTransaction borrowingTransaction) {
        return borrowingTransactionRepo.save(borrowingTransaction);
    }

    @Override
    public List<BorrowingTransaction> getTransactionsByMember(Long memberId) {
        return borrowingTransactionRepo.findByMemberId(memberId);
    }

    @Override
    public List<BorrowingTransaction> getAllTransactions() {
        return borrowingTransactionRepo.findAll();
    }
}
