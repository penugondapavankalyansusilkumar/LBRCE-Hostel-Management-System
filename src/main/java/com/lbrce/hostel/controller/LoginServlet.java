package com.lbrce.hostel.controller;

import com.lbrce.hostel.dao.UserDAO;
import com.lbrce.hostel.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private UserDAO userDAO;

    @Override
    public void init() {
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            request.setAttribute("error", "Please enter your username and password.");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }

        try {
            User user = userDAO.authenticate(username.trim(), password);

            if (user == null) {
                request.setAttribute("error", "Invalid username or password.");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
                return;
            }

            String role = user.getRole();
            if (role == null || role.isBlank()) {
                request.setAttribute("error", "Your account does not have a valid role.");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
                return;
            }

            role = role.trim().toUpperCase();

            HttpSession oldSession = request.getSession(false);
            if (oldSession != null) oldSession.invalidate();

            HttpSession session = request.getSession(true);
            session.setAttribute("userId", user.getId());
            session.setAttribute("username", user.getUsername());
            session.setAttribute("role", role);
            session.setMaxInactiveInterval(30 * 60);

            // ✅ Always redirect to unified dashboard
            response.sendRedirect(request.getContextPath() + "/dashboard");

        } catch (SQLException e) {
            getServletContext().log("Login database operation failed.", e);
            e.printStackTrace();
            request.setAttribute("error", "Unable to process login right now. Please check the server log.");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        } catch (RuntimeException e) {
            getServletContext().log("Unexpected error during login.", e);
            e.printStackTrace();
            request.setAttribute("error", "An unexpected error occurred. Please try again.");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }
}
