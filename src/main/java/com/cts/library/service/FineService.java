package com.cts.library.service;

public interface FineService {

    /**
     * Scheduled method to process fines daily.
     * - Creates a new fine on the first overdue day.
     * - Increments existing fine by ₹20 for each additional overdue day.
     */
    void processDailyFines();

    /**
     * Marks a fine as paid.
     * @param fineId ID of the fine to be paid
     */
    void payFine(Long fineId);
}
