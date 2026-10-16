package com.lbrce.hostel.controller;

import com.lbrce.hostel.dao.AttendanceDAO;
import com.lbrce.hostel.dao.ComplaintDAO;
import com.lbrce.hostel.dao.DutyShiftDAO;
import com.lbrce.hostel.dao.FeeDAO;
import com.lbrce.hostel.dao.MessDAO;
import com.lbrce.hostel.dao.NoticeDAO;
import com.lbrce.hostel.dao.OutingDAO;
import com.lbrce.hostel.dao.RoomDAO;
import com.lbrce.hostel.dao.StudentDAO;
import com.lbrce.hostel.dao.WardenDAO;
import com.lbrce.hostel.model.Attendance;
import com.lbrce.hostel.model.Complaint;
import com.lbrce.hostel.model.DutyShift;
import com.lbrce.hostel.model.Fee;
import com.lbrce.hostel.model.MessBill;
import com.lbrce.hostel.model.Notice;
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
        "/admin/dashboard",
        "/admin/students",
        "/admin/rooms",
        "/admin/wardens",
        "/admin/fees",
        "/admin/complaints",
        "/admin/outings",
        "/admin/duty-shifts",
        "/admin/mess-bills",
        "/admin/attendance",
        "/admin/reports"
})
public class AdminServlet extends HttpServlet {

    private StudentDAO studentDAO;
    private RoomDAO roomDAO;
    private WardenDAO wardenDAO;
    private FeeDAO feeDAO;
    private ComplaintDAO complaintDAO;
    private OutingDAO outingDAO;
    private DutyShiftDAO dutyShiftDAO;
    private MessDAO messDAO;
    private AttendanceDAO attendanceDAO;
    private NoticeDAO noticeDAO;

    @Override
    public void init() {
        studentDAO = new StudentDAO();
        roomDAO = new RoomDAO();
        wardenDAO = new WardenDAO();
        feeDAO = new FeeDAO();
        complaintDAO = new ComplaintDAO();
        outingDAO = new OutingDAO();
        dutyShiftDAO = new DutyShiftDAO();
        messDAO = new MessDAO();
        attendanceDAO = new AttendanceDAO();
        noticeDAO = new NoticeDAO();
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null
                || session.getAttribute("userId") == null
                || !"ADMIN".equals(session.getAttribute("role"))) {

            response.sendRedirect(
                    request.getContextPath() + "/login");
            return;
        }

        String path = request.getServletPath();

        try {
            switch (path) {
                case "/admin/students" -> {
                    List<Student> students = studentDAO.getAllStudents();
                    request.setAttribute("students", students);
                    request.getRequestDispatcher("/admin/students.jsp")
                            .forward(request, response);
                }
                case "/admin/rooms" -> {
                    List<Room> rooms = roomDAO.getAllRooms();
                    request.setAttribute("rooms", rooms);
                    request.getRequestDispatcher("/admin/rooms.jsp")
                            .forward(request, response);
                }
                case "/admin/wardens" -> {
                    List<Warden> wardens = wardenDAO.getAllWardens();
                    request.setAttribute("wardens", wardens);
                    request.getRequestDispatcher("/admin/wardens.jsp")
                            .forward(request, response);
                }
                case "/admin/fees" -> {
                    List<Fee> fees = feeDAO.getAllFees();
                    request.setAttribute("fees", fees);
                    request.getRequestDispatcher("/admin/fees.jsp")
                            .forward(request, response);
                }
                case "/admin/complaints" -> {
                    List<Complaint> complaints = complaintDAO.getAllComplaints();
                    request.setAttribute("complaints", complaints);
                    request.getRequestDispatcher("/admin/complaints.jsp")
                            .forward(request, response);
                }
                case "/admin/outings" -> {
                    List<Outing> outings = outingDAO.getAllOutings();
                    request.setAttribute("outings", outings);
                    request.getRequestDispatcher("/admin/outings.jsp")
                            .forward(request, response);
                }
                case "/admin/duty-shifts" -> {
                    List<DutyShift> dutyShifts = dutyShiftDAO.getAllDutyShifts();
                    request.setAttribute("dutyShifts", dutyShifts);
                    request.getRequestDispatcher("/admin/duty-shifts.jsp")
                            .forward(request, response);
                }
                case "/admin/mess-bills" -> {
                    List<MessBill> bills = messDAO.getAllBills();
                    request.setAttribute("bills", bills);
                    request.getRequestDispatcher("/admin/mess-bills.jsp")
                            .forward(request, response);
                }
                case "/admin/attendance" -> {
                    List<Attendance> attendanceRecords = attendanceDAO.getAllAttendance();
                    request.setAttribute("attendanceRecords", attendanceRecords);
                    request.getRequestDispatcher("/admin/attendance.jsp")
                            .forward(request, response);
                }
                case "/admin/reports" -> {
                    List<Student> students = studentDAO.getAllStudents();
                    List<Room> rooms = roomDAO.getAllRooms();
                    List<Fee> fees = feeDAO.getAllFees();
                    List<Complaint> complaints = complaintDAO.getAllComplaints();

                    request.setAttribute("studentCount", students.size());
                    request.setAttribute("roomCount", rooms.size());
                    request.setAttribute("feeCount", fees.size());
                    request.setAttribute("complaintCount", complaints.size());
                    request.setAttribute("students", students);
                    request.setAttribute("rooms", rooms);
                    request.setAttribute("fees", fees);

                    request.getRequestDispatcher("/admin/reports.jsp")
                            .forward(request, response);
                }
                default -> {
                    // /admin/dashboard
                    List<Student> students = studentDAO.getAllStudents();
                    List<Room> rooms = roomDAO.getAllRooms();
                    List<Warden> wardens = wardenDAO.getAllWardens();
                    List<Fee> fees = feeDAO.getAllFees();
                    List<Complaint> complaints = complaintDAO.getAllComplaints();
                    List<Outing> outings = outingDAO.getAllOutings();
                    List<Notice> notices = noticeDAO.getAllNotices();

                    request.setAttribute("students", students);
                    request.setAttribute("rooms", rooms);
                    request.setAttribute("wardens", wardens);
                    request.setAttribute("fees", fees);
                    request.setAttribute("complaints", complaints);
                    request.setAttribute("outings", outings);

                    request.setAttribute("studentCount", students.size());
                    request.setAttribute("roomCount", rooms.size());
                    request.setAttribute("wardenCount", wardens.size());
                    request.setAttribute("complaintCount", complaints.size());
                    request.setAttribute("outingCount", outings.size());
                    request.setAttribute("noticeCount", notices.size());

                    request.getRequestDispatcher("/admin/dashboard.jsp")
                            .forward(request, response);
                }
            }

        } catch (SQLException e) {
            getServletContext().log(
                    "Unable to load the requested admin page: " + path, e);

            request.setAttribute(
                    "error",
                    "Unable to load information. Please try again later.");

            request.getRequestDispatcher("/error.jsp")
                    .forward(request, response);
        }
    }
}