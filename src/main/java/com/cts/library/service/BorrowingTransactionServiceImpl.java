package com.cts.library.service;

import com.cts.library.model.Book;
import com.cts.library.model.BorrowingTransaction;
import com.cts.library.model.Member;
import com.cts.library.repository.BookRepo;
import com.cts.library.repository.BorrowingTransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BorrowingTransactionServiceImpl implements BorrowingTransactionService {

    private BorrowingTransactionRepo borrowingTransactionRepo;

    private BookRepo bookRepo;

    private MemberService memberService;

   
    private BookService bookService;

    public BorrowingTransactionServiceImpl(BorrowingTransactionRepo borrowingTransactionRepo, BookRepo bookRepo,
			MemberService memberService, BookService bookService) {
		super();
		this.borrowingTransactionRepo = borrowingTransactionRepo;
		this.bookRepo = bookRepo;
		this.memberService = memberService;
		this.bookService = bookService;
	}

    @Override
    public BorrowingTransaction borrowBook(Long memberId, Long bookId) {
        Member member = memberService.getMemberById(memberId);
        Book book = bookService.getBookById(bookId);

        if (book.getAvailableCopies() <= 0) {
            throw new RuntimeException("Book is currently unavailable.");
        }

        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookRepo.save(book);

        BorrowingTransaction transaction = new BorrowingTransaction();
        transaction.setBook(book);
        transaction.setMember(member);
        transaction.setBorrowDate(LocalDate.now());
        transaction.setReturnDate(null);
        transaction.setStatus(BorrowingTransaction.Status.BORROWED);

        return borrowingTransactionRepo.save(transaction);
    }

    @Override
    public BorrowingTransaction returnBook(Long transactionId) {
        BorrowingTransaction transaction = borrowingTransactionRepo.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        if (transaction.getStatus() == BorrowingTransaction.Status.RETURNED) {
            throw new RuntimeException("Book already returned.");
        }

        Book book = transaction.getBook();
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        bookRepo.save(book);

        transaction.setReturnDate(LocalDate.now());
        transaction.setStatus(BorrowingTransaction.Status.RETURNED);

        return borrowingTransactionRepo.save(transaction);
    }

    @Override
    public List<BorrowingTransaction> getTransactionsByMember(Long memberId) {
        return borrowingTransactionRepo.findByMember_MemberId(memberId);
    }

    @Override
    public List<BorrowingTransaction> getAllTransactions() {
        return borrowingTransactionRepo.findAll();
    }
}
	