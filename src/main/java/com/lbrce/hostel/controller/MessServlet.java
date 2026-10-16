package com.lbrce.hostel.controller;

import com.lbrce.hostel.dao.MessDAO;
import com.lbrce.hostel.dao.StudentDAO;
import com.lbrce.hostel.model.MessBill;
import com.lbrce.hostel.model.MessMenu;
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

@WebServlet("/student/mess")
public class MessServlet extends HttpServlet {

    private MessDAO messDAO;
    private StudentDAO studentDAO;

    @Override
    public void init() {
        messDAO = new MessDAO();
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

            List<MessBill> bills =
                    messDAO.getBillsByStudentId(student.getId());

            List<MessMenu> menu =
                    messDAO.getAllMenu();

            request.setAttribute("student", student);
            request.setAttribute("bills", bills);
            request.setAttribute("menu", menu);

            request.getRequestDispatcher("/student/mess.jsp")
                    .forward(request, response);

        } catch (SQLException e) {
            getServletContext().log(
                    "Unable to load student mess information.", e);

            request.setAttribute(
                    "error",
                    "Unable to load mess information. Please try again later.");

            request.getRequestDispatcher("/error.jsp")
                    .forward(request, response);
        }
    }
}