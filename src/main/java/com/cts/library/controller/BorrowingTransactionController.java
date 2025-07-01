package com.cts.library.controller;

import com.cts.library.service.BorrowingTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;


@RestController
//@RequestMapping("/api/transactions")
public class BorrowingTransactionController {

    @Autowired
    private BorrowingTransactionService transactionService;

    @PostMapping("/borrow/{memberId}/{bookId}")
    @PreAuthorize("hasRole('ADMIN')")
    public String borrowBook(@RequestParam Long bookId, @RequestParam Long memberId) {
        return transactionService.borrowBook(bookId, memberId);
    }

    @PostMapping("/return/{memberId}/{bookId}")
    @PreAuthorize("hasRole('ADMIN')")
    public String returnBook(@RequestParam Long bookId, @RequestParam Long memberId) {
        return transactionService.returnBook(bookId, memberId);
    }
}