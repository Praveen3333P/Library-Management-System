package com.cts.library.controller;

import com.cts.library.service.BorrowingTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;


@RestController
@RequestMapping("/books")
public class BorrowingTransactionController {

    @Autowired
    private BorrowingTransactionService transactionService;

    @PostMapping("/borrow/{memberId}/{bookId}")
<<<<<<< Updated upstream
    public String borrowBook(@PathVariable Long bookId, @PathVariable Long memberId) {
=======
    public String borrowBook(@RequestParam Long bookId, @RequestParam Long memberId) {
>>>>>>> Stashed changes
        return transactionService.borrowBook(bookId, memberId);
    }

    @PostMapping("/return/{memberId}/{bookId}")
<<<<<<< Updated upstream
    public String returnBook(@PathVariable Long bookId, @PathVariable Long memberId) {
=======
    public String returnBook(@RequestParam Long bookId, @RequestParam Long memberId) {
>>>>>>> Stashed changes
        return transactionService.returnBook(bookId, memberId);
    }
}