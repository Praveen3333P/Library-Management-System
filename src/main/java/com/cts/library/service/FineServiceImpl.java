package com.cts.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.cts.library.model.Fine;
import com.cts.library.repository.FineRepo;
import com.cts.library.repository.MemberRepo;

@Service
public class FineServiceImpl implements FineService {

	@Autowired
	private MemberRepo memberRepo;
	@Autowired
	private FineRepo fineRepo;

	public FineServiceImpl(MemberRepo memberRepo, FineRepo fineRepo) {
		this.memberRepo = memberRepo;
		this.fineRepo = fineRepo;
	}

	/**
	 * Scheduled method that runs daily at 1 AM to process fines. - Creates a new
	 * fine on the first overdue day. - Increments existing fine by ₹20 for each
	 * additional overdue day.
	 */
//    @Override
//    @Scheduled(cron = "00 19 16 * * ?") // Every day at 3:43 PM
//    public void processDailyFines() {
//        List<Member> members = memberRepo.findAllWithTransactions();
//        LocalDate today = LocalDate.now();
//
//        for (Member member : members) {
//        	Set<BorrowingTransaction> transactions = member.getTransactions();
//
//            for (BorrowingTransaction tx : transactions) {
//                if (tx.getStatus() == BorrowingTransaction.Status.BORROWED &&
//                    tx.getReturnDate().isBefore(today)) {
//
//                    Optional<Fine> existingFineOpt = member.getFines().stream()
//                        .filter(f -> f.getTransaction().equals(tx) &&
//                                     "PENDING".equalsIgnoreCase(f.getFineStatus()))
//                        .findFirst();
//
//                    if (existingFineOpt.isPresent()) {
//                        Fine existingFine = existingFineOpt.get();
//                        long overdueDays = ChronoUnit.DAYS.between(tx.getReturnDate(), today);
//                        existingFine.setAmount(overdueDays* 20.0);
//                        existingFine.setTransactionDate(today);
//                        fineRepo.save(existingFine);
//                    } else {
//                        Fine newFine = new Fine();
//                        newFine.setMember(member);
//                        newFine.setAmount(20.0);
//                        newFine.setFineStatus("PENDING");
//                        newFine.setTransaction(tx);
//                        newFine.setTransactionDate(today);
//
//                        Fine savedFine = fineRepo.save(newFine);
//                        member.getFines().add(savedFine);
//                        memberRepo.save(member);
//                    }
//                }
//            }
//        }
//    }

	@Override
	@Scheduled(cron = "0 20 12 * * ?") // Every day at 3:43 PM
	public void processDailyFines() {
		fineRepo.insertPendingFinesForOverdueTransactions();
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
	}
}
