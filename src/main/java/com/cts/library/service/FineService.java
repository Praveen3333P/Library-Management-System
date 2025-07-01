package com.cts.library.service;

import com.cts.library.model.Fine;
import com.cts.library.model.BorrowingTransaction;

import java.util.List;

public interface FineService {
    List<Fine> getFinesByMemberId(Long memberId);
    Fine payFine(Long fineId);
    List<Fine> evaluateAndCreateOverdueFines(List<BorrowingTransaction> transactions);
    void payFinesAndRemoveFromList(List<Fine> fines);
    Fine markFineAsPaid(Fine fine);
}
