package com.lbrce.hostel.controller;

import com.lbrce.hostel.util.DBConnection;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/db-test")
public class DatabaseTestServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        response.setContentType("text/plain;charset=UTF-8");

        try (Connection connection = DBConnection.getConnection()) {

            response.getWriter().println(
                    "DATABASE CONNECTION SUCCESSFUL");

            response.getWriter().println(
                    "Database: " + connection.getCatalog());

            String sql =
                    "SELECT id, username, role FROM users LIMIT 5";

            try (PreparedStatement statement =
                         connection.prepareStatement(sql);
                 ResultSet rs = statement.executeQuery()) {

                response.getWriter().println();
                response.getWriter().println("USER TABLE:");

                while (rs.next()) {
                    response.getWriter().println(
                            rs.getInt("id") + " | "
                                    + rs.getString("username") + " | "
                                    + rs.getString("role"));
                }
            }

        } catch (SQLException e) {

            response.setStatus(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

            response.getWriter().println(
                    "DATABASE CONNECTION OR QUERY FAILED");

            response.getWriter().println(
                    "Error type: " + e.getClass().getSimpleName());

            response.getWriter().println(
                    "Error message: " + e.getMessage());
        }
    }
}