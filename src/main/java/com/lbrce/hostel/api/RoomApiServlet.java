package com.lbrce.hostel.api;

import com.lbrce.hostel.dao.RoomDAO;
import com.lbrce.hostel.model.Room;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/api/rooms")
public class RoomApiServlet extends HttpServlet {

    private RoomDAO roomDAO;

    @Override
    public void init() {
        roomDAO = new RoomDAO();
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

        if (!"ADMIN".equals(role)
                && !"WARDEN".equals(role)
                && !"STUDENT".equals(role)) {

            response.sendError(
                    HttpServletResponse.SC_FORBIDDEN,
                    "You do not have permission to view rooms.");
            return;
        }

        try {
            List<Room> rooms = roomDAO.getAllRooms();

            StringBuilder json = new StringBuilder("[");
            boolean first = true;

            for (Room room : rooms) {

                if (!first) {
                    json.append(",");
                }

                json.append("{")
                        .append("\"id\":").append(room.getId()).append(",")
                        .append("\"roomNumber\":\"")
                        .append(escapeJson(room.getRoomNumber())).append("\",")
                        .append("\"roomType\":\"")
                        .append(escapeJson(room.getRoomType())).append("\",")
                        .append("\"capacity\":").append(room.getCapacity()).append(",")
                        .append("\"occupied\":").append(room.getOccupied()).append(",")
                        .append("\"monthlyFee\":").append(room.getMonthlyFee()).append(",")
                        .append("\"status\":\"")
                        .append(escapeJson(room.getStatus())).append("\"")
                        .append("}");

                first = false;
            }

            json.append("]");

            response.getWriter().write(json.toString());

        } catch (SQLException e) {
            getServletContext().log(
                    "Unable to retrieve room API data.", e);

            response.sendError(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Unable to retrieve room information.");
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