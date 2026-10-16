package com.lbrce.hostel.controller;

import com.lbrce.hostel.dao.AttendanceDAO;
import com.lbrce.hostel.dao.StudentDAO;
import com.lbrce.hostel.model.Attendance;
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

@WebServlet("/student/attendance")
public class AttendanceServlet extends HttpServlet {

    private AttendanceDAO attendanceDAO;
    private StudentDAO studentDAO;

    @Override
    public void init() {
        attendanceDAO = new AttendanceDAO();
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

            List<Attendance> attendanceRecords =
                    attendanceDAO.getAttendanceByStudentId(
                            student.getId());

            request.setAttribute("student", student);
            request.setAttribute(
                    "attendanceRecords", attendanceRecords);

            request.getRequestDispatcher(
                            "/student/attendance.jsp")
                    .forward(request, response);

        } catch (SQLException e) {
            getServletContext().log(
                    "Unable to load student attendance.", e);

            request.setAttribute(
                    "error",
                    "Unable to load attendance records. Please try again later.");

            request.getRequestDispatcher("/error.jsp")
                    .forward(request, response);
        }
    }
}