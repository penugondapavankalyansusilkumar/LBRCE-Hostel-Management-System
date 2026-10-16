package com.lbrce.hostel.dao;

import com.lbrce.hostel.model.Outing;
import com.lbrce.hostel.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class OutingDAO {

    // Student submits an outing request
    public boolean addOuting(
            int studentId,
            String reason,
            Date outingDate,
            Date expectedReturnDate) throws SQLException {

        if (reason == null || reason.isBlank()
                || outingDate == null
                || expectedReturnDate == null) {
            throw new IllegalArgumentException(
                    "All outing details are required.");
        }

        if (expectedReturnDate.before(outingDate)) {
            throw new IllegalArgumentException(
                    "Return date cannot be before the outing date.");
        }

        String sql = """
                INSERT INTO outings
                    (student_id, reason, outing_date,
                     expected_return_date, status)
                VALUES (?, ?, ?, ?, 'PENDING')
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, studentId);
            statement.setString(2, reason.trim());
            statement.setDate(3, outingDate);
            statement.setDate(4, expectedReturnDate);

            return statement.executeUpdate() == 1;
        }
    }

    // Student views their own outing requests
    public List<Outing> getOutingsByStudentId(int studentId)
            throws SQLException {

        List<Outing> outings = new ArrayList<>();

        String sql = """
                SELECT id, student_id, reason, outing_date,
                       expected_return_date, status, created_at
                FROM outings
                WHERE student_id = ?
                ORDER BY created_at DESC
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, studentId);

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    outings.add(mapOuting(rs));
                }
            }
        }

        return outings;
    }

    // Authorized staff view all outing requests
    public List<Outing> getAllOutings() throws SQLException {

        List<Outing> outings = new ArrayList<>();

        String sql = """
                SELECT id, student_id, reason, outing_date,
                       expected_return_date, status, created_at
                FROM outings
                ORDER BY created_at DESC
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                outings.add(mapOuting(rs));
            }
        }

        return outings;
    }

    // Warden approves or rejects an outing request
    public boolean updateOutingStatus(
            int outingId,
            String status) throws SQLException {

        if (status == null
                || !List.of("APPROVED", "REJECTED", "PENDING")
                .contains(status.toUpperCase())) {
            throw new IllegalArgumentException(
                    "Invalid outing status.");
        }

        String sql = """
                UPDATE outings
                SET status = ?
                WHERE id = ?
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, status.toUpperCase());
            statement.setInt(2, outingId);

            return statement.executeUpdate() == 1;
        }
    }

    // Convert a database row into an Outing object
    private Outing mapOuting(ResultSet rs)
            throws SQLException {

        Outing outing = new Outing();

        outing.setId(rs.getInt("id"));
        outing.setStudentId(rs.getInt("student_id"));
        outing.setReason(rs.getString("reason"));

        Date outingDate = rs.getDate("outing_date");
        if (outingDate != null) {
            outing.setOutingDate(outingDate.toLocalDate());
        }

        Date returnDate = rs.getDate("expected_return_date");
        if (returnDate != null) {
            outing.setExpectedReturnDate(returnDate.toLocalDate());
        }

        outing.setStatus(rs.getString("status"));

        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) {
            outing.setCreatedAt(createdAt.toLocalDateTime());
        }

        return outing;
    }
}