package com.cts.library.service;

import com.cts.library.model.BorrowingTransaction;
import com.cts.library.model.Fine;
import com.cts.library.model.Member;
import com.cts.library.repository.FineRepo;
import com.cts.library.repository.MemberRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class FineServiceImpl implements FineService {

    @Autowired
    private MemberRepo memberRepo;

    @Autowired
    private FineRepo fineRepo;

    /**
     * Scheduled method that runs daily at 1 AM to process fines.
     * - Creates a new fine on the first overdue day.
     * - Increments existing fine by ₹20 for each additional overdue day.
     */
    @Override
    @Scheduled(cron = "0 0 1 * * ?") // Every day at 1 AM
    public void processDailyFines() {
        List<Member> members = memberRepo.findAllWithTransactions();
        LocalDate today = LocalDate.now();

        for (Member member : members) {
            List<BorrowingTransaction> transactions = member.getTransactions();

            for (BorrowingTransaction tx : transactions) {
                if (tx.getStatus() == BorrowingTransaction.Status.BORROWED &&
                    tx.getReturnDate().isBefore(today)) {

                    Optional<Fine> existingFineOpt = member.getFines().stream()
                        .filter(f -> f.getTransaction().equals(tx) &&
                                     "PENDING".equalsIgnoreCase(f.getFineStatus()))
                        .findFirst();

                    if (existingFineOpt.isPresent()) {
                        // 🔁 Update existing fine
                        Fine existingFine = existingFineOpt.get();
                        existingFine.setAmount(existingFine.getAmount() + 20.0);
                        existingFine.setTransactionDate(today);
                        fineRepo.save(existingFine);
                    } else {
                        // 🆕 Create new fine
                        Fine newFine = new Fine();
                        newFine.setMember(member);
                        newFine.setAmount(20.0);
                        newFine.setFineStatus("PENDING");
                        newFine.setTransactionDate(today);

                        Fine savedFine = fineRepo.save(newFine);
                        member.getFines().add(savedFine);
                        memberRepo.save(member);
                    }
                }
            }
        }
    }

    /**
     * Marks a fine as paid by updating its status to "PAID".
     * @param fineId ID of the fine to be paid
     */
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
