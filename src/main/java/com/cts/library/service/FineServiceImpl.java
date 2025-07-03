package com.cts.library.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.cts.library.model.BorrowingTransaction;
import com.cts.library.model.Fine;
import com.cts.library.repository.BorrowingTransactionRepo;
import com.cts.library.repository.FineRepo;
import com.cts.library.repository.MemberRepo;

@Service
public class FineServiceImpl implements FineService {

    private BorrowingTransactionRepo borrowingTransactionRepo;

	@Autowired
	private MemberRepo memberRepo;
	@Autowired
	private FineRepo fineRepo;

	public FineServiceImpl(MemberRepo memberRepo, FineRepo fineRepo, BorrowingTransactionRepo borrowingTransactionRepo) {
		this.memberRepo = memberRepo;
		this.fineRepo = fineRepo;
		this.borrowingTransactionRepo = borrowingTransactionRepo;
	}

	/**
	 * Scheduled method that runs daily at 1 AM to process fines. - Creates a new
	 * fine on the first overdue day. - Increments existing fine by ₹20 for each
	 * additional overdue day.
	 */

	@Override

	@Scheduled(cron = "0 16 17 * * ?") // Every day at 3:43 PM
	public void processDailyFines() {
		fineRepo.insertPendingFinesForOverdueTransactions();
		fineRepo.updatePendingFineAmountsDaily();
	}
	
	@Override
	public void payFine(Long fineId) {
	    Fine fine = fineRepo.findById(fineId)
	            .orElseThrow(() -> new RuntimeException("Fine not found with ID: " + fineId));

	    if ("PAID".equalsIgnoreCase(fine.getFineStatus())) {
	        throw new IllegalStateException("Fine is already paid.");
	    }
	    
	    fine.setFineStatus("PAID");
	    fineRepo.save(fine);

	    BorrowingTransaction transaction = fine.getTransaction();
	    transaction.setStatus(BorrowingTransaction.Status.RETURNED);
	    transaction.setReturnDate(LocalDate.now());
	    borrowingTransactionRepo.save(transaction);
	}

}
