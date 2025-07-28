package com.cts.library.repository;

import com.cts.library.model.BorrowingTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cts.library.model.Book;
import java.util.List;

@Repository
public interface BorrowingTransactionRepo extends JpaRepository<BorrowingTransaction, Long> {
    List<BorrowingTransaction> findByMember_MemberId(Long memberId);
    void deleteByMember_MemberId(Long memberId);
    boolean existsByBook_BookIdAndStatus(Long bookId, BorrowingTransaction.Status status);
    
    @Query("SELECT bt FROM BorrowingTransaction bt")
    List<BorrowingTransaction> findAllWithMemberAndBook();

    @Query("SELECT bt.book FROM BorrowingTransaction bt WHERE bt.member.memberId = :memberId AND bt.status = 'BORROWED'")
    List<Book> findBooksByBorrowedTransactions(Long memberId);

}

