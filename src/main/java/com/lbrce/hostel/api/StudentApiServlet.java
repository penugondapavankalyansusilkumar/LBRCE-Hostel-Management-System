package com.lbrce.hostel.api;

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
import java.util.List;

@WebServlet("/api/students")
public class StudentApiServlet extends HttpServlet {

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

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Cache-Control", "no-store");

        HttpSession session = request.getSession(false);

        if (session == null
                || session.getAttribute("userId") == null
                || session.getAttribute("role") == null) {

            response.sendError(
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "Please log in first.");
            return;
        }

        String role = session.getAttribute("role").toString();

        if (!"ADMIN".equals(role) && !"WARDEN".equals(role)) {
            response.sendError(
                    HttpServletResponse.SC_FORBIDDEN,
                    "You do not have permission to view all students.");
            return;
        }

        try {
            List<Student> students = studentDAO.getAllStudents();

            StringBuilder json = new StringBuilder("[");
            boolean first = true;

            for (Student student : students) {

                if (!first) {
                    json.append(",");
                }

                json.append("{")
                        .append("\"id\":").append(student.getId()).append(",")
                        .append("\"rollNumber\":\"")
                        .append(escapeJson(student.getRollNumber())).append("\",")
                        .append("\"fullName\":\"")
                        .append(escapeJson(student.getFullName())).append("\",")
                        .append("\"email\":\"")
                        .append(escapeJson(student.getEmail())).append("\",")
                        .append("\"course\":\"")
                        .append(escapeJson(student.getCourse())).append("\"")
                        .append("}");

                first = false;
            }

            json.append("]");

            response.getWriter().write(json.toString());

        } catch (SQLException e) {
            getServletContext().log(
                    "Unable to retrieve student API data.", e);

            response.sendError(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Unable to retrieve student information.");
        }
    }

    private String escapeJson(String value) {
        if (value == null) {
            return "";
        }

        return value
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }
}