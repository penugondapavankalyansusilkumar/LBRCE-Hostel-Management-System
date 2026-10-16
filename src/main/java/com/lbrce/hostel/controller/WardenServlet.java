package com.lbrce.hostel.controller;

import com.lbrce.hostel.dao.AttendanceDAO;
import com.lbrce.hostel.dao.ComplaintDAO;
import com.lbrce.hostel.dao.DutyShiftDAO;
import com.lbrce.hostel.dao.FeeDAO;
import com.lbrce.hostel.dao.MessDAO;
import com.lbrce.hostel.dao.OutingDAO;
import com.lbrce.hostel.dao.RoomDAO;
import com.lbrce.hostel.dao.StudentDAO;
import com.lbrce.hostel.dao.WardenDAO;
import com.lbrce.hostel.model.Attendance;
import com.lbrce.hostel.model.Complaint;
import com.lbrce.hostel.model.DutyShift;
import com.lbrce.hostel.model.Fee;
import com.lbrce.hostel.model.MessMenu;
import com.lbrce.hostel.model.Outing;
import com.lbrce.hostel.model.Room;
import com.lbrce.hostel.model.Student;
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

@WebServlet({
        "/warden/dashboard",
        "/warden/students",
        "/warden/rooms",
        "/warden/attendance",
        "/warden/complaints",
        "/warden/fees",
        "/warden/outings",
        "/warden/mess-menu",
        "/warden/duty-schedule"
})
public class WardenServlet extends HttpServlet {

    private WardenDAO wardenDAO;
    private DutyShiftDAO dutyShiftDAO;
    private ComplaintDAO complaintDAO;
    private OutingDAO outingDAO;
    private StudentDAO studentDAO;
    private RoomDAO roomDAO;
    private AttendanceDAO attendanceDAO;
    private FeeDAO feeDAO;
    private MessDAO messDAO;

    @Override
    public void init() {
        wardenDAO = new WardenDAO();
        dutyShiftDAO = new DutyShiftDAO();
        complaintDAO = new ComplaintDAO();
        outingDAO = new OutingDAO();
        studentDAO = new StudentDAO();
        roomDAO = new RoomDAO();
        attendanceDAO = new AttendanceDAO();
        feeDAO = new FeeDAO();
        messDAO = new MessDAO();
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
        String path = request.getServletPath();

        try {
            Warden warden = wardenDAO.getWardenByUserId(userId);

            if (warden == null) {
                request.setAttribute(
                        "error",
                        "Warden profile not found. Please contact the administrator.");

                request.getRequestDispatcher("/error.jsp")
                        .forward(request, response);
                return;
            }

            request.setAttribute("warden", warden);

            switch (path) {
                case "/warden/students" -> {
                    List<Student> students = studentDAO.getAllStudents();
                    request.setAttribute("students", students);
                    request.getRequestDispatcher("/warden/students.jsp")
                            .forward(request, response);
                }
                case "/warden/rooms" -> {
                    List<Room> rooms = roomDAO.getAllRooms();
                    request.setAttribute("rooms", rooms);
                    request.getRequestDispatcher("/warden/rooms.jsp")
                            .forward(request, response);
                }
                case "/warden/attendance" -> {
                    List<Attendance> attendanceRecords = attendanceDAO.getAllAttendance();
                    request.setAttribute("attendanceRecords", attendanceRecords);
                    request.getRequestDispatcher("/warden/attendance.jsp")
                            .forward(request, response);
                }
                case "/warden/complaints" -> {
                    List<Complaint> complaints = complaintDAO.getAllComplaints();
                    request.setAttribute("complaints", complaints);
                    request.getRequestDispatcher("/warden/complaints.jsp")
                            .forward(request, response);
                }
                case "/warden/fees" -> {
                    List<Fee> fees = feeDAO.getAllFees();
                    request.setAttribute("fees", fees);
                    request.getRequestDispatcher("/warden/fees.jsp")
                            .forward(request, response);
                }
                case "/warden/outings" -> {
                    List<Outing> outings = outingDAO.getAllOutings();
                    request.setAttribute("outings", outings);
                    request.getRequestDispatcher("/warden/outings.jsp")
                            .forward(request, response);
                }
                case "/warden/mess-menu" -> {
                    List<MessMenu> menu = messDAO.getAllMenu();
                    request.setAttribute("menu", menu);
                    request.getRequestDispatcher("/warden/mess-menu.jsp")
                            .forward(request, response);
                }
                case "/warden/duty-schedule" -> {
                    List<DutyShift> dutyShifts =
                            dutyShiftDAO.getShiftsByWardenId(warden.getId());
                    request.setAttribute("dutyShifts", dutyShifts);
                    request.getRequestDispatcher("/warden/duty-schedule.jsp")
                            .forward(request, response);
                }
                default -> {
                    // /warden/dashboard
                    List<DutyShift> dutyShifts =
                            dutyShiftDAO.getShiftsByWardenId(warden.getId());
                    List<Complaint> complaints =
                            complaintDAO.getAllComplaints();
                    List<Outing> outings =
                            outingDAO.getAllOutings();
                    List<Student> students =
                            studentDAO.getAllStudents();
                    List<Room> rooms =
                            roomDAO.getAllRooms();

                    request.setAttribute("dutyShifts", dutyShifts);
                    request.setAttribute("complaints", complaints);
                    request.setAttribute("outings", outings);
                    request.setAttribute("studentCount", students.size());
                    request.setAttribute("roomCount", rooms.size());
                    request.setAttribute("shiftCount", dutyShifts.size());
                    request.setAttribute("complaintCount", complaints.size());
                    request.setAttribute("outingCount", outings.size());

                    request.getRequestDispatcher("/warden/dashboard.jsp")
                            .forward(request, response);
                }
            }

        } catch (SQLException e) {
            getServletContext().log(
                    "Unable to load warden page: " + path, e);

            request.setAttribute(
                    "error",
                    "Unable to load information. Please try again later.");

            request.getRequestDispatcher("/error.jsp")
                    .forward(request, response);
        }
    }
}