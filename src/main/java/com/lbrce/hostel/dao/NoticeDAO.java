package com.lbrce.hostel.dao;

import com.lbrce.hostel.model.Notice;
import com.lbrce.hostel.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class NoticeDAO {

    // Retrieve all hostel notices
    public List<Notice> getAllNotices() throws SQLException {

        List<Notice> notices = new ArrayList<>();

        String sql = """
                SELECT id, title, content, created_by, created_at
                FROM notices
                ORDER BY created_at DESC
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                notices.add(mapNotice(rs));
            }
        }

        return notices;
    }

    // Retrieve one notice by ID
    public Notice getNoticeById(int noticeId)
            throws SQLException {

        String sql = """
                SELECT id, title, content, created_by, created_at
                FROM notices
                WHERE id = ?
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, noticeId);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return mapNotice(rs);
                }
            }
        }

        return null;
    }

    // Create a hostel notice
    public boolean addNotice(
            String title,
            String content,
            int createdBy) throws SQLException {

        if (title == null || title.isBlank()
                || content == null || content.isBlank()) {
            throw new IllegalArgumentException(
                    "Notice title and content are required.");
        }

        String sql = """
                INSERT INTO notices (title, content, created_by)
                VALUES (?, ?, ?)
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, title.trim());
            statement.setString(2, content.trim());
            statement.setInt(3, createdBy);

            return statement.executeUpdate() == 1;
        }
    }

    // Delete a notice
    public boolean deleteNotice(int noticeId)
            throws SQLException {

        String sql = """
                DELETE FROM notices
                WHERE id = ?
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, noticeId);

            return statement.executeUpdate() == 1;
        }
    }

    // Convert a database row into a Notice object
    private Notice mapNotice(ResultSet rs)
            throws SQLException {

        Notice notice = new Notice();

        notice.setId(rs.getInt("id"));
        notice.setTitle(rs.getString("title"));
        notice.setContent(rs.getString("content"));

        // Convert the database user ID to a String
        notice.setCreatedBy(
                String.valueOf(rs.getInt("created_by")));

        Timestamp createdAt = rs.getTimestamp("created_at");

        if (createdAt != null) {
            notice.setCreatedAt(createdAt.toLocalDateTime());
        }

        return notice;
    }
}

