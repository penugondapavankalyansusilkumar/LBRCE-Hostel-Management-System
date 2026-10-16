package com.lbrce.hostel.service;

import com.lbrce.hostel.model.Fee;

public class FeeService {

    public double calculateBalance(Fee fee) {

        if (fee == null) {
            throw new IllegalArgumentException(
                    "Fee record cannot be null.");
        }

        double total = fee.getAmount();
        double paid = fee.getPaidAmount();

        if (total < 0 || paid < 0 || paid > total) {
            throw new IllegalArgumentException(
                    "Invalid fee amounts.");
        }

        return total - paid;
    }

    public String determinePaymentStatus(Fee fee) {

        double total = fee.getAmount();
        double paid = fee.getPaidAmount();

        if (total < 0 || paid < 0 || paid > total) {
            throw new IllegalArgumentException(
                    "Invalid fee amounts.");
        }

        if (paid == 0) {
            return "PENDING";
        }

        if (paid < total) {
            return "PARTIAL";
        }

        return "PAID";
    }
}