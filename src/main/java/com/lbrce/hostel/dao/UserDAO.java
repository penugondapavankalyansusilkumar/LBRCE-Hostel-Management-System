package com.lbrce.hostel.dao;

import com.lbrce.hostel.model.User;
import com.lbrce.hostel.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    public User authenticate(String username, String password)
            throws SQLException {

        String sql = """
                SELECT id, username, password, role
                FROM users
                WHERE username = ?
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, username);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {

                    String storedPassword =
                            resultSet.getString("password");

                    if (passwordMatches(password, storedPassword)) {

                        User user = new User();

                        user.setId(resultSet.getInt("id"));
                        user.setUsername(
                                resultSet.getString("username"));
                        user.setPassword(storedPassword);
                        user.setRole(
                                resultSet.getString("role"));

                        return user;
                    }
                }
            }
        }

        return null;
    }

    private boolean passwordMatches(
            String enteredPassword,
            String storedPassword) {

        if (enteredPassword == null || storedPassword == null) {
            return false;
        }

        // Support existing plain-text test accounts.
        if (enteredPassword.equals(storedPassword)) {
            return true;
        }

        // Support passwords hashed with PasswordUtil.
        String hashedPassword =
                com.lbrce.hostel.util.PasswordUtil
                        .hashPassword(enteredPassword);

        return hashedPassword.equalsIgnoreCase(storedPassword);
    }
}