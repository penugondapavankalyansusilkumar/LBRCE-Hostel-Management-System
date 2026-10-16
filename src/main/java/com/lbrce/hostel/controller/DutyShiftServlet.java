package com.lbrce.hostel.controller;

import com.lbrce.hostel.dao.DutyShiftDAO;
import com.lbrce.hostel.dao.WardenDAO;
import com.lbrce.hostel.model.DutyShift;
import com.lbrce.hostel.model.Warden;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/warden/duty-shifts")
public class DutyShiftServlet extends HttpServlet {

    private DutyShiftDAO dutyShiftDAO;
    private WardenDAO wardenDAO;

    @Override
    public void init() {
        dutyShiftDAO = new DutyShiftDAO();
        wardenDAO = new WardenDAO();
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null
                || session.getAttribute("userId") == null
                || !"WARDEN".equals(session.getAttribute("role"))) {

            response.sendRedirect(
                    request.getContextPath() + "/login");
            return;
        }

        int userId = (Integer) session.getAttribute("userId");

        try {
            Warden warden = wardenDAO.getWardenByUserId(userId);

            if (warden == null) {
                response.sendError(
                        HttpServletResponse.SC_NOT_FOUND,
                        "Warden profile not found.");
                return;
            }

            List<DutyShift> dutyShifts =
                    dutyShiftDAO.getShiftsByWardenId(warden.getId());

            request.setAttribute("warden", warden);
            request.setAttribute("dutyShifts", dutyShifts);

            request.getRequestDispatcher("/warden/duty-schedule.jsp")
                    .forward(request, response);

        } catch (SQLException e) {
            getServletContext().log(
                    "Unable to load warden duty shifts.", e);

            request.setAttribute(
                    "error",
                    "Unable to load duty shifts. Please try again later.");

            request.getRequestDispatcher("/error.jsp")
                    .forward(request, response);
        }
    }
}