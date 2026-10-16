package com.lbrce.hostel.controller;

import com.lbrce.hostel.dao.StudentDAO;
import com.lbrce.hostel.model.Student;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet({"/student/dashboard", "/student/profile"})
public class StudentServlet extends HttpServlet {

    private StudentDAO studentDAO;

    @Override
    public void init() {
        studentDAO = new StudentDAO();
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null
                || session.getAttribute("userId") == null
                || !"STUDENT".equals(session.getAttribute("role"))) {

            response.sendRedirect(
                    request.getContextPath() + "/login");
            return;
        }

        int userId = (Integer) session.getAttribute("userId");
        String path = request.getServletPath();

        try {
            Student student = studentDAO.getStudentByUserId(userId);

            if (student == null) {
                request.setAttribute(
                        "error",
                        "Student profile was not found. Please contact the hostel administrator.");

                request.getRequestDispatcher("/error.jsp")
                        .forward(request, response);
                return;
            }

            request.setAttribute("student", student);

            String targetJsp = "/student/profile".equals(path)
                    ? "/student/profile.jsp"
                    : "/student/dashboard.jsp";

            request.getRequestDispatcher(targetJsp)
                    .forward(request, response);

        } catch (SQLException e) {
            getServletContext().log(
                    "Unable to load student dashboard.", e);

            request.setAttribute(
                    "error",
                    "Unable to load your dashboard. Please try again later.");

            request.getRequestDispatcher("/error.jsp")
                    .forward(request, response);
        }
    }
}