package com.cts.library.service;

import com.cts.library.model.Fine;
import com.cts.library.model.BorrowingTransaction;
import com.cts.library.repository.FineRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class FineServiceImpl implements FineService {

    @Autowired
    private FineRepo fineRepo;

    private static final double DAILY_FINE_RATE = 20.0;

    @Override
    public List<Fine> getFinesByMemberId(Long memberId) {
        return fineRepo.findByMemberMemberId(memberId);
    }

    @Override
    public Fine payFine(Long fineId) {
        Optional<Fine> optionalFine = fineRepo.findById(fineId);
        if (optionalFine.isPresent()) {
            Fine fine = optionalFine.get();
            if (!"Paid".equalsIgnoreCase(fine.getStatus())) {
                fine.setStatus("Paid");
                fine.setTransactionDate(LocalDate.now());
                return fineRepo.save(fine);
            }
        }
        return null;
    }

    @Override
    public List<Fine> evaluateAndCreateOverdueFines(List<BorrowingTransaction> transactions) {
        List<Fine> fines = new ArrayList<>();
        LocalDate today = LocalDate.now();

        for (BorrowingTransaction transaction : transactions) {
            LocalDate dueDate = transaction.getReturnDate();
            if (dueDate != null && dueDate.isBefore(today)) {
                long overdueDays = ChronoUnit.DAYS.between(dueDate, today);
                double totalFine = overdueDays * DAILY_FINE_RATE;

                Fine fine = new Fine();
                fine.setMember(transaction.getMember());
                fine.setAmount(totalFine);
                fine.setStatus("Pending");
                fine.setTransactionDate(today);

                fines.add(fineRepo.save(fine));
            }
        }

        return fines;
    }

    @Override
    public void payFinesAndRemoveFromList(List<Fine> fines) {
        Iterator<Fine> iterator = fines.iterator();
        while (iterator.hasNext()) {
            Fine fine = iterator.next();
            if (!"Paid".equalsIgnoreCase(fine.getStatus())) {
                fine.setStatus("Paid");
                fine.setTransactionDate(LocalDate.now());
                fineRepo.save(fine);
            }
            iterator.remove(); // remove from list
        }
    }
}
