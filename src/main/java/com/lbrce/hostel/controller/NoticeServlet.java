package com.lbrce.hostel.controller;

import com.lbrce.hostel.dao.NoticeDAO;
import com.lbrce.hostel.model.Notice;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/notices")
public class NoticeServlet extends HttpServlet {

    private NoticeDAO noticeDAO;

    @Override
    public void init() {
        noticeDAO = new NoticeDAO();
    }

    private boolean isLoggedIn(HttpSession session) {
        return session != null
                && session.getAttribute("userId") != null
                && session.getAttribute("role") != null;
    }

    private boolean isAdmin(HttpSession session) {
        return isLoggedIn(session)
                && "ADMIN".equals(session.getAttribute("role"));
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (!isLoggedIn(session)) {
            response.sendRedirect(
                    request.getContextPath() + "/login");
            return;
        }

        try {
            List<Notice> notices = noticeDAO.getAllNotices();

            request.setAttribute("notices", notices);
            request.setAttribute("isAdmin", isAdmin(session));

            request.getRequestDispatcher("/notices.jsp")
                    .forward(request, response);

        } catch (SQLException e) {
            getServletContext().log(
                    "Unable to load hostel notices.", e);

            request.setAttribute(
                    "error",
                    "Unable to load notices. Please try again later.");

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

        if (!isAdmin(session)) {
            response.sendError(
                    HttpServletResponse.SC_FORBIDDEN,
                    "Only administrators can manage notices.");
            return;
        }

        String action = request.getParameter("action");

        try {
            if ("add".equals(action)) {
                String title = request.getParameter("title");
                String content = request.getParameter("content");

                if (title == null || title.isBlank()
                        || content == null || content.isBlank()
                        || title.trim().length() > 150
                        || content.trim().length() > 5000) {

                    response.sendError(
                            HttpServletResponse.SC_BAD_REQUEST,
                            "Enter a valid notice title and content.");
                    return;
                }

                int userId = (Integer) session.getAttribute("userId");

                noticeDAO.addNotice(
                        title.trim(),
                        content.trim(),
                        userId);

            } else if ("delete".equals(action)) {
                String idText = request.getParameter("id");
                if (idText == null || idText.isBlank()) {
                    idText = request.getParameter("noticeId");
                }

                if (idText == null || idText.isBlank()) {
                    response.sendError(
                            HttpServletResponse.SC_BAD_REQUEST,
                            "Notice ID is required.");
                    return;
                }

                int noticeId = Integer.parseInt(idText);

                if (noticeId <= 0) {
                    response.sendError(
                            HttpServletResponse.SC_BAD_REQUEST,
                            "Invalid notice ID.");
                    return;
                }

                noticeDAO.deleteNotice(noticeId);

            } else {
                response.sendError(
                        HttpServletResponse.SC_BAD_REQUEST,
                        "Invalid notice action.");
                return;
            }

            response.sendRedirect(
                    request.getContextPath() + "/notices?success=1");

        } catch (NumberFormatException e) {
            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Invalid notice ID.");

        } catch (SQLException e) {
            getServletContext().log(
                    "Unable to manage hostel notices.", e);

            response.sendError(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Unable to manage notices. Please try again later.");
        }
    }
}