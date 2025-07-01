package com.cts.library.controller;

import com.cts.library.model.BorrowingTransaction;
import com.cts.library.service.BorrowingTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RequestMapping("/api/transactions")
public class BorrowingTransactionController {

    @Autowired
    private BorrowingTransactionService transactionService;

    @PostMapping("/borrow/{memberId}/{bookId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BorrowingTransaction> borrowBook(
            @PathVariable Long memberId,
            @PathVariable Long bookId) {
        return ResponseEntity.ok(transactionService.borrowBook(memberId, bookId));
    }

    @PostMapping("/return/{transactionId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BorrowingTransaction> returnBook(@PathVariable Long transactionId) {
        return ResponseEntity.ok(transactionService.returnBook(transactionId));
    }

    @GetMapping("/member/{memberId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<BorrowingTransaction>> getByMember(@PathVariable Long memberId) {
        return ResponseEntity.ok(transactionService.getTransactionsByMember(memberId));
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<BorrowingTransaction>> getAll() {
        return ResponseEntity.ok(transactionService.getAllTransactions());
    }
}
