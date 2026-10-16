package com.lbrce.hostel.controller;

import com.lbrce.hostel.dao.OutingDAO;
import com.lbrce.hostel.dao.StudentDAO;
import com.lbrce.hostel.model.Outing;
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

@WebServlet("/student/outings")
public class OutingServlet extends HttpServlet {

    private OutingDAO outingDAO;
    private StudentDAO studentDAO;

    @Override
    public void init() {
        outingDAO = new OutingDAO();
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

            List<Outing> outings =
                    outingDAO.getOutingsByStudentId(student.getId());

            request.setAttribute("student", student);
            request.setAttribute("outings", outings);

            request.getRequestDispatcher("/student/outings.jsp")
                    .forward(request, response);

        } catch (SQLException e) {
            getServletContext().log(
                    "Unable to load student outing requests.", e);

            request.setAttribute(
                    "error",
                    "Unable to load outing requests. Please try again later.");

            request.getRequestDispatcher("/error.jsp")
                    .forward(request, response);
        }
    }

    @Override
    protected void doPost(
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

        String reason = request.getParameter("reason");
        String outingDateText = request.getParameter("outingDate");
        String returnDateText = request.getParameter("expectedReturnDate");

        if (reason == null || reason.isBlank()
                || outingDateText == null || outingDateText.isBlank()
                || returnDateText == null || returnDateText.isBlank()) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Please complete all outing request fields.");
            return;
        }

        try {
            java.sql.Date outingDate =
                    java.sql.Date.valueOf(outingDateText);

            java.sql.Date returnDate =
                    java.sql.Date.valueOf(returnDateText);

            if (reason.length() > 500
                    || returnDate.before(outingDate)) {

                response.sendError(
                        HttpServletResponse.SC_BAD_REQUEST,
                        "Check the reason and outing dates.");
                return;
            }

            int userId = (Integer) session.getAttribute("userId");

            Student student = studentDAO.getStudentByUserId(userId);

            if (student == null) {
                response.sendError(
                        HttpServletResponse.SC_NOT_FOUND,
                        "Student profile not found.");
                return;
            }

            outingDAO.addOuting(
                    student.getId(),
                    reason.trim(),
                    outingDate,
                    returnDate);

            response.sendRedirect(
                    request.getContextPath()
                            + "/student/outings?success=1");

        } catch (IllegalArgumentException e) {
            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Invalid date format. Please select valid dates.");

        } catch (SQLException e) {
            getServletContext().log(
                    "Unable to submit outing request.", e);

            response.sendError(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Unable to submit the outing request. Please try again later.");
        }
    }
}