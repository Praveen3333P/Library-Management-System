package com.cts.library.controller;

import com.cts.library.service.BorrowingTransactionService;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/books")
public class BorrowingTransactionController {

    private final BorrowingTransactionService transactionService;

    public BorrowingTransactionController(BorrowingTransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/borrow/{memberId}/{bookId}")
    public String borrowBook(@PathVariable Long memberId, @PathVariable Long bookId) {
        return transactionService.borrowBook(bookId, memberId);
    }

    @PostMapping("/return/{memberId}/{bookId}")
    public String returnBook(@PathVariable Long bookId, @PathVariable Long memberId) {
        return transactionService.returnBook(bookId, memberId);
    }
}
