package com.cts.library.service;

import com.cts.library.model.Book;
import com.cts.library.model.BorrowingTransaction;
import com.cts.library.model.Member;
import com.cts.library.repository.BookRepo;
import com.cts.library.repository.BorrowingTransactionRepo;
import com.cts.library.repository.MemberRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BorrowingTransactionServiceImpl implements BorrowingTransactionService {

    @Autowired
    private BorrowingTransactionRepo transactionRepo;

    @Autowired
    private BookRepo bookRepo;

    @Autowired
    private MemberRepo memberRepo;

    @Override
    public String borrowBook(Long bookId, Long memberId) {
        Book book = bookRepo.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));
        Member member = memberRepo.findById(memberId).orElseThrow(() -> new RuntimeException("Member not found"));

        if (book.getAvailableCopies() <= 0) {
            return "No copies available for borrowing.";
        }
        
        if (member.getBorrowingLimit() <= 0) {
            return "Borrowing limit reached. Cannot borrow more books.";
        }
        
        
        BorrowingTransaction txn = new BorrowingTransaction();
        txn.setBook(book);
        txn.setMember(member);
        txn.setBorrowDate(LocalDate.now());
        txn.setReturnDate(LocalDate.now().plusDays(10));
        txn.setStatus(BorrowingTransaction.Status.BORROWED);

        transactionRepo.save(txn);
        
        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookRepo.save(book);

        member.setBorrowingLimit(member.getBorrowingLimit() - 1);
        memberRepo.save(member);
        
        return "Book borrowed successfully.";
    }

    @Override
    public String returnBook(Long bookId, Long memberId) {
        Book book = bookRepo.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));
        Member member = memberRepo.findById(memberId).orElseThrow(() -> new RuntimeException("Member not found"));

        Optional<BorrowingTransaction> optTxn = transactionRepo.findByMember_MemberId(member.getMemberId()).stream()
            .filter(txn -> txn.getBook().equals(book) && txn.getStatus() == BorrowingTransaction.Status.BORROWED)
            .findFirst();

        if (!optTxn.isPresent()) return "No borrowed transaction found.";

        BorrowingTransaction txn = optTxn.get();
        txn.setReturnDate(LocalDate.now());
        txn.setStatus(BorrowingTransaction.Status.RETURNED);
        transactionRepo.save(txn);
        
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        bookRepo.save(book);

        member.setBorrowingLimit(member.getBorrowingLimit() + 1);
        memberRepo.save(member);
        
        return "Book returned successfully.";
    }
}
	