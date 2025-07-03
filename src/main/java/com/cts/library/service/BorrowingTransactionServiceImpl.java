package com.cts.library.service;

import com.cts.library.authentication.CurrentUser;
import com.cts.library.exception.UnauthorizedAccessException;
import com.cts.library.model.Book;
import com.cts.library.model.BorrowingTransaction;
import com.cts.library.model.Member;
import com.cts.library.repository.BookRepo;
import com.cts.library.repository.BorrowingTransactionRepo;
import com.cts.library.repository.MemberRepo;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class BorrowingTransactionServiceImpl implements BorrowingTransactionService {
    
	private final BorrowingTransactionRepo transactionRepo;
	private final BookRepo bookRepo;
	private final MemberRepo memberRepo;
	private final CurrentUser currentUser;

	public BorrowingTransactionServiceImpl(BorrowingTransactionRepo transactionRepo, BookRepo bookRepo,
			MemberRepo memberRepo, CurrentUser currentUser) {
		this.transactionRepo = transactionRepo;
		this.bookRepo = bookRepo;
		this.memberRepo = memberRepo;
		this.currentUser = currentUser;
	}


	public String borrowBook(Long bookId, Long memberId) {
    


		if (!validateCurrentUser(memberId)) {
			return "Validation Error";
		}

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
		System.out.println("txn"+txn);
		transactionRepo.save(txn);

		book.setAvailableCopies(book.getAvailableCopies() - 1);
		bookRepo.save(book);

		member.setBorrowingLimit(member.getBorrowingLimit() - 1);
		memberRepo.save(member);

		return "Book borrowed successfully.";
	}

	public String returnBook(Long bookId, Long memberId) {
		validateCurrentUser(memberId);

		Book book = bookRepo.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));

		Member member = memberRepo.findById(memberId).orElseThrow(() -> new RuntimeException("Member not found"));

		Optional<BorrowingTransaction> optTxn = transactionRepo.findByMember_MemberId(memberId).stream()
				.filter(txn -> txn.getBook().equals(book) && txn.getStatus() == BorrowingTransaction.Status.BORROWED)
				.findFirst();

		if (optTxn.isEmpty()) {
			return "No borrowed transaction found.";
		}

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

	private Boolean validateCurrentUser(Long memberId) {
		Long currentUserId = currentUser.getCurrentUser().getMemberId();
		if (!currentUserId.equals(memberId)) {

			throw new UnauthorizedAccessException("You don't have rights to act on behalf of another member.");
		}
		return true;
	}
}
