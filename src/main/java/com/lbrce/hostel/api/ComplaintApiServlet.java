package com.lbrce.hostel.api;

import com.lbrce.hostel.dao.ComplaintDAO;
import com.lbrce.hostel.model.Complaint;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/api/complaints")
public class ComplaintApiServlet extends HttpServlet {

    private ComplaintDAO complaintDAO;

    @Override
    public void init() {
        complaintDAO = new ComplaintDAO();
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
                    "You cannot access all complaints.");
            return;
        }

        try {
            List<Complaint> complaints =
                    complaintDAO.getAllComplaints();

            StringBuilder json = new StringBuilder("[");
            boolean first = true;

            for (Complaint complaint : complaints) {

                if (!first) {
                    json.append(",");
                }

                json.append("{")
                        .append("\"id\":").append(complaint.getId()).append(",")
                        .append("\"studentId\":")
                        .append(complaint.getStudentId()).append(",")
                        .append("\"subject\":\"")
                        .append(escapeJson(complaint.getSubject())).append("\",")
                        .append("\"description\":\"")
                        .append(escapeJson(complaint.getDescription())).append("\",")
                        .append("\"status\":\"")
                        .append(escapeJson(complaint.getStatus())).append("\"")
                        .append("}");

                first = false;
            }

            json.append("]");

            response.getWriter().write(json.toString());

        } catch (SQLException e) {
            getServletContext().log(
                    "Unable to retrieve complaint API data.", e);

            response.sendError(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Unable to retrieve complaints.");
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