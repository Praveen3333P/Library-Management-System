package com.cts.library.controller;

import com.cts.library.model.BorrowingTransaction;
import com.cts.library.service.BorrowingTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class BorrowingTransactionController {

    @Autowired
    private BorrowingTransactionService transactionService;

    @PostMapping("/borrow")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BorrowingTransaction> borrowBook(@RequestBody BorrowingTransaction transaction) {
        transaction.setStatus(BorrowingTransaction.Status.BORROWED);
        BorrowingTransaction savedTransaction = transactionService.saveBorrowingTransaction(transaction);
        return ResponseEntity.ok(savedTransaction);
    }

    @PostMapping("/return/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BorrowingTransaction> returnBook(@PathVariable Long id) {
        List<BorrowingTransaction> all = transactionService.getAllTransactions();
        for (BorrowingTransaction tx : all) {
            if (tx.getTransactionId().equals(id)) {
                tx.setStatus(BorrowingTransaction.Status.RETURNED);
                tx.setReturnDate(java.time.LocalDate.now());
                BorrowingTransaction updated = transactionService.saveBorrowingTransaction(tx);
                return ResponseEntity.ok(updated);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/member/{memberId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<BorrowingTransaction>> getByMember(@PathVariable Long memberId) {
        return ResponseEntity.ok(transactionService.getTransactionsByMember(memberId));
    }

    @GetMapping("/alltransactions")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<BorrowingTransaction>> getAll() {
        return ResponseEntity.ok(transactionService.getAllTransactions());
    }
}
