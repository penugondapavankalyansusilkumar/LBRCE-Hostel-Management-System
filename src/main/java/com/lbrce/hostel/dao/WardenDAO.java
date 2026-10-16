package com.lbrce.hostel.dao;

import com.lbrce.hostel.model.Warden;
import com.lbrce.hostel.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WardenDAO {

    // Get all wardens
    public List<Warden> getAllWardens() throws SQLException {

        List<Warden> wardens = new ArrayList<>();

        String sql = """
                SELECT id, user_id, full_name, phone, email
                FROM wardens
                ORDER BY full_name
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                wardens.add(mapWarden(rs));
            }
        }

        return wardens;
    }

    // Find a warden using the associated user account ID
    public Warden getWardenByUserId(int userId)
            throws SQLException {

        String sql = """
                SELECT id, user_id, full_name, phone, email
                FROM wardens
                WHERE user_id = ?
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, userId);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return mapWarden(rs);
                }
            }
        }

        return null;
    }

    // Add a warden
    public boolean addWarden(
            int userId,
            String fullName,
            String phone,
            String email) throws SQLException {

        if (fullName == null || fullName.isBlank()) {
            throw new IllegalArgumentException(
                    "Warden name is required.");
        }

        String sql = """
                INSERT INTO wardens
                    (user_id, full_name, phone, email)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, userId);
            statement.setString(2, fullName.trim());
            statement.setString(3, phone);
            statement.setString(4, email);

            return statement.executeUpdate() == 1;
        }
    }

    // Update warden contact details
    public boolean updateWarden(
            int wardenId,
            String fullName,
            String phone,
            String email) throws SQLException {

        if (fullName == null || fullName.isBlank()) {
            throw new IllegalArgumentException(
                    "Warden name is required.");
        }

        String sql = """
                UPDATE wardens
                SET full_name = ?, phone = ?, email = ?
                WHERE id = ?
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, fullName.trim());
            statement.setString(2, phone);
            statement.setString(3, email);
            statement.setInt(4, wardenId);

            return statement.executeUpdate() == 1;
        }
    }

    // Convert a database row into a Warden object
    private Warden mapWarden(ResultSet rs)
            throws SQLException {

        Warden warden = new Warden();

        warden.setId(rs.getInt("id"));
        warden.setUserId(rs.getInt("user_id"));
        warden.setFullName(rs.getString("full_name"));
        warden.setPhone(rs.getString("phone"));
        warden.setEmail(rs.getString("email"));

        return warden;
    }
}