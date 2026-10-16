package com.lbrce.hostel.dao;

import com.lbrce.hostel.model.Fee;
import com.lbrce.hostel.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class FeeDAO {

    // Get all fee records for a particular student
    public List<Fee> getFeesByStudentId(int studentId)
            throws SQLException {

        List<Fee> fees = new ArrayList<>();

        String sql = """
                SELECT id, student_id, amount, paid_amount,
                       due_date, status
                FROM fees
                WHERE student_id = ?
                ORDER BY due_date DESC
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, studentId);

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    fees.add(mapFee(rs));
                }
            }
        }

        return fees;
    }

    // Get all fee records for authorized staff
    public List<Fee> getAllFees() throws SQLException {

        List<Fee> fees = new ArrayList<>();

        String sql = """
                SELECT id, student_id, amount, paid_amount,
                       due_date, status
                FROM fees
                ORDER BY due_date DESC
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                fees.add(mapFee(rs));
            }
        }

        return fees;
    }

    // Update a fee payment record
    public boolean updatePayment(
            int feeId,
            double paidAmount,
            String status) throws SQLException {

        if (paidAmount < 0) {
            throw new IllegalArgumentException(
                    "Paid amount cannot be negative.");
        }

        if (status == null
                || !List.of("PENDING", "PARTIAL", "PAID")
                .contains(status.toUpperCase())) {
            throw new IllegalArgumentException(
                    "Invalid fee status.");
        }

        String sql = """
                UPDATE fees
                SET paid_amount = ?, status = ?
                WHERE id = ?
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setDouble(1, paidAmount);
            statement.setString(2, status.toUpperCase());
            statement.setInt(3, feeId);

            return statement.executeUpdate() == 1;
        }
    }

    private Fee mapFee(ResultSet rs) throws SQLException {

        Fee fee = new Fee();

        fee.setId(rs.getInt("id"));
        fee.setStudentId(rs.getInt("student_id"));
        fee.setAmount(rs.getDouble("amount"));
        fee.setPaidAmount(rs.getDouble("paid_amount"));

        Date dueDate = rs.getDate("due_date");

        if (dueDate != null) {
            fee.setDueDate(dueDate.toLocalDate());
        }

        fee.setStatus(rs.getString("status"));

        return fee;
    }
}