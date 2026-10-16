package com.lbrce.hostel.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("userId") == null || session.getAttribute("role") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String role = session.getAttribute("role").toString().toUpperCase();

        switch (role) {
            case "ADMIN" -> response.sendRedirect(request.getContextPath() + "/admin/dashboard");
            case "WARDEN" -> response.sendRedirect(request.getContextPath() + "/warden/dashboard");
            default -> response.sendRedirect(request.getContextPath() + "/student/dashboard");
        }
    }
}
