package com.cts.library.controller;

import com.cts.library.service.BorrowingTransactionService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/books")
public class BorrowingTransactionController {

    private final BorrowingTransactionService transactionService;

    public BorrowingTransactionController(BorrowingTransactionService transactionService) {
        this.transactionService = transactionService;
    }


@PostMapping("/borrow/{memberId}/{bookId}")
public ResponseEntity<String> borrowBook(@PathVariable Long memberId, @PathVariable Long bookId) {
    String result = transactionService.borrowBook(bookId, memberId);

    if (result.equals("Book borrowed successfully!")) {
        return ResponseEntity.ok(result); 
    } else {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }
}

    @PostMapping("/return/{memberId}/{bookId}")
    public String returnBook(@PathVariable Long bookId, @PathVariable Long memberId) {
        return transactionService.returnBook(bookId, memberId);
    }
}
