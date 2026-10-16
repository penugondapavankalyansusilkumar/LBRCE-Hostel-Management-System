package com.lbrce.hostel.api;

import com.lbrce.hostel.dao.FeeDAO;
import com.lbrce.hostel.model.Fee;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/api/fees")
public class FeeApiServlet extends HttpServlet {

    private FeeDAO feeDAO;

    @Override
    public void init() {
        feeDAO = new FeeDAO();
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

        if (!"ADMIN".equals(role)) {
            response.sendError(
                    HttpServletResponse.SC_FORBIDDEN,
                    "Only administrators can access all fee records.");
            return;
        }

        try {
            List<Fee> fees = feeDAO.getAllFees();

            StringBuilder json = new StringBuilder("[");
            boolean first = true;

            for (Fee fee : fees) {

                if (!first) {
                    json.append(",");
                }

                json.append("{")
                        .append("\"id\":").append(fee.getId()).append(",")
                        .append("\"studentId\":")
                        .append(fee.getStudentId()).append(",")
                        .append("\"amount\":").append(fee.getAmount()).append(",")
                        .append("\"paidAmount\":").append(fee.getPaidAmount()).append(",")
                        .append("\"dueDate\":\"")
                        .append(fee.getDueDate() == null
                                ? ""
                                : fee.getDueDate().toString())
                        .append("\",")
                        .append("\"status\":\"")
                        .append(escapeJson(fee.getStatus())).append("\"")
                        .append("}");

                first = false;
            }

            json.append("]");

            response.getWriter().write(json.toString());

        } catch (SQLException e) {
            getServletContext().log(
                    "Unable to retrieve fee API data.", e);

            response.sendError(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Unable to retrieve fee records.");
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