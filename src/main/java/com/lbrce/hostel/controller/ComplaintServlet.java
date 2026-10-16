package com.lbrce.hostel.controller;

import com.lbrce.hostel.dao.ComplaintDAO;
import com.lbrce.hostel.dao.StudentDAO;
import com.lbrce.hostel.model.Complaint;
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

@WebServlet("/student/complaints")
public class ComplaintServlet extends HttpServlet {

    private ComplaintDAO complaintDAO;
    private StudentDAO studentDAO;

    @Override
    public void init() {
        complaintDAO = new ComplaintDAO();
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

            List<Complaint> complaints =
                    complaintDAO.getComplaintsByStudentId(
                            student.getId());

            request.setAttribute("student", student);
            request.setAttribute("complaints", complaints);

            request.getRequestDispatcher(
                            "/student/complaints.jsp")
                    .forward(request, response);

        } catch (SQLException e) {
            getServletContext().log(
                    "Unable to load student complaints.", e);

            request.setAttribute(
                    "error",
                    "Unable to load complaints. Please try again later.");

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

        int userId = (Integer) session.getAttribute("userId");

        String subject = request.getParameter("subject");
        String description = request.getParameter("description");

        if (subject == null || subject.isBlank()
                || description == null || description.isBlank()) {

            request.setAttribute(
                    "error",
                    "Please enter both a subject and description.");

            doGetWithError(request, response);
            return;
        }

        try {
            Student student = studentDAO.getStudentByUserId(userId);

            if (student == null) {
                response.sendError(
                        HttpServletResponse.SC_NOT_FOUND,
                        "Student profile not found.");
                return;
            }

            boolean added = complaintDAO.addComplaint(
                    student.getId(), subject, description);

            if (added) {
                response.sendRedirect(
                        request.getContextPath()
                                + "/student/complaints?success=1");
            } else {
                request.setAttribute(
                        "error", "Unable to submit your complaint.");

                doGetWithError(request, response);
            }

        } catch (SQLException e) {
            getServletContext().log(
                    "Unable to submit student complaint.", e);

            request.setAttribute(
                    "error",
                    "Unable to submit your complaint. Please try again later.");

            doGetWithError(request, response);

        } catch (IllegalArgumentException e) {
            request.setAttribute("error", e.getMessage());
            doGetWithError(request, response);
        }
    }

    private void doGetWithError(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int userId = (Integer) request.getSession(false)
                    .getAttribute("userId");

            Student student = studentDAO.getStudentByUserId(userId);

            if (student != null) {
                request.setAttribute("student", student);
                request.setAttribute(
                        "complaints",
                        complaintDAO.getComplaintsByStudentId(
                                student.getId()));
            }

            request.getRequestDispatcher(
                            "/student/complaints.jsp")
                    .forward(request, response);

        } catch (SQLException e) {
            getServletContext().log(
                    "Unable to reload complaints page.", e);

            request.setAttribute(
                    "error",
                    "Unable to display the complaints page.");

            request.getRequestDispatcher("/error.jsp")
                    .forward(request, response);
        }
    }
}