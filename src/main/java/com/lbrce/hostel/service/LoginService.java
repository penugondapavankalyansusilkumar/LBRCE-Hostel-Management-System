package com.lbrce.hostel.service;

import com.lbrce.hostel.dao.UserDAO;
import com.lbrce.hostel.model.User;

import java.sql.SQLException;

public class LoginService {

    private final UserDAO userDAO;

    public LoginService() {
        this.userDAO = new UserDAO();
    }

    public User login(String username, String password)
            throws SQLException {

        if (username == null || username.isBlank()
                || password == null || password.isBlank()) {
            return null;
        }

        return userDAO.authenticate(
                username.trim(),
                password
        );
    }
}