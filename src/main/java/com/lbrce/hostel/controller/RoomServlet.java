package com.lbrce.hostel.controller;

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

@WebServlet("/student/rooms")
public class RoomServlet extends HttpServlet {

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

        HttpSession session = request.getSession(false);

        if (session == null
                || session.getAttribute("userId") == null
                || !"STUDENT".equals(session.getAttribute("role"))) {

            response.sendRedirect(
                    request.getContextPath() + "/login");
            return;
        }

        try {
            List<Room> rooms = roomDAO.getAllRooms();

            request.setAttribute("rooms", rooms);

            request.getRequestDispatcher("/student/room.jsp")
                    .forward(request, response);

        } catch (SQLException e) {
            getServletContext().log(
                    "Unable to load hostel rooms.", e);

            request.setAttribute(
                    "error",
                    "Unable to load room information. Please try again later.");

            request.getRequestDispatcher("/error.jsp")
                    .forward(request, response);
        }
    }
}