package com.lbrce.hostel.controller;

import com.lbrce.hostel.dao.FeeDAO;
import com.lbrce.hostel.dao.StudentDAO;
import com.lbrce.hostel.model.Fee;
import com.lbrce.hostel.model.Student;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/student/fees")
public class FeeServlet extends HttpServlet {

    private FeeDAO feeDAO;
    private StudentDAO studentDAO;

    @Override
    public void init() {
        feeDAO = new FeeDAO();
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

        try {
            Student student = studentDAO.getStudentByUserId(userId);

            if (student == null) {
                response.sendError(
                        HttpServletResponse.SC_NOT_FOUND,
                        "Student profile not found.");
                return;
            }

            List<Fee> fees =
                    feeDAO.getFeesByStudentId(student.getId());

            request.setAttribute("student", student);
            request.setAttribute("fees", fees);

            request.getRequestDispatcher("/student/fees.jsp")
                    .forward(request, response);

        } catch (SQLException e) {
            getServletContext().log(
                    "Unable to load student fee records.", e);

            request.setAttribute(
                    "error",
                    "Unable to load fee details. Please try again later.");

            request.getRequestDispatcher("/error.jsp")
                    .forward(request, response);
        }
    }
}