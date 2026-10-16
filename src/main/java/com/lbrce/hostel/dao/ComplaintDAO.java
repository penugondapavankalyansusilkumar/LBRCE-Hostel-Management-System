package com.lbrce.hostel.dao;

import com.lbrce.hostel.model.Complaint;
import com.lbrce.hostel.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ComplaintDAO {

    public boolean addComplaint(
            int studentId,
            String subject,
            String description) throws SQLException {

        if (subject == null || subject.isBlank()
                || description == null || description.isBlank()) {
            throw new IllegalArgumentException(
                    "Subject and description are required.");
        }

        String sql = """
                INSERT INTO complaints
                    (student_id, subject, description, status)
                VALUES (?, ?, ?, 'PENDING')
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, studentId);
            statement.setString(2, subject.trim());
            statement.setString(3, description.trim());

            return statement.executeUpdate() == 1;
        }
    }

    public List<Complaint> getComplaintsByStudentId(
            int studentId) throws SQLException {

        List<Complaint> complaints = new ArrayList<>();

        String sql = """
                SELECT id, student_id, subject, description,
                       status, created_at
                FROM complaints
                WHERE student_id = ?
                ORDER BY created_at DESC
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, studentId);

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    complaints.add(mapComplaint(rs));
                }
            }
        }

        return complaints;
    }

    public List<Complaint> getAllComplaints() throws SQLException {

        List<Complaint> complaints = new ArrayList<>();

        String sql = """
                SELECT id, student_id, subject, description,
                       status, created_at
                FROM complaints
                ORDER BY created_at DESC
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                complaints.add(mapComplaint(rs));
            }
        }

        return complaints;
    }

    public boolean updateComplaintStatus(
            int complaintId,
            String status) throws SQLException {

        if (status == null
                || !List.of("PENDING", "IN_PROGRESS", "RESOLVED")
                .contains(status.toUpperCase())) {
            throw new IllegalArgumentException(
                    "Invalid complaint status.");
        }

        String sql = """
                UPDATE complaints
                SET status = ?
                WHERE id = ?
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, status.toUpperCase());
            statement.setInt(2, complaintId);

            return statement.executeUpdate() == 1;
        }
    }

    private Complaint mapComplaint(ResultSet rs)
            throws SQLException {

        Complaint complaint = new Complaint();

        complaint.setId(rs.getInt("id"));
        complaint.setStudentId(rs.getInt("student_id"));
        complaint.setSubject(rs.getString("subject"));
        complaint.setDescription(rs.getString("description"));
        complaint.setStatus(rs.getString("status"));

        Timestamp createdAt = rs.getTimestamp("created_at");

        if (createdAt != null) {
            complaint.setCreatedAt(createdAt.toLocalDateTime());
        }

        return complaint;
    }
}