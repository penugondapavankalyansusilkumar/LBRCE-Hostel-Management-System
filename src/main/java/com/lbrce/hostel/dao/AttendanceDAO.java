package com.lbrce.hostel.dao;

import com.lbrce.hostel.model.Attendance;
import com.lbrce.hostel.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AttendanceDAO {

    public List<Attendance> getAttendanceByStudentId(
            int studentId) throws SQLException {

        List<Attendance> attendanceList = new ArrayList<>();

        String sql = """
                SELECT id, student_id, attendance_date, status
                FROM attendance
                WHERE student_id = ?
                ORDER BY attendance_date DESC
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, studentId);

            try (ResultSet rs = statement.executeQuery()) {

                while (rs.next()) {
                    Attendance attendance = new Attendance();

                    attendance.setId(rs.getInt("id"));
                    attendance.setStudentId(
                            rs.getInt("student_id"));

                    Date date = rs.getDate("attendance_date");

                    if (date != null) {
                        attendance.setAttendanceDate(
                                date.toLocalDate());
                    }

                    attendance.setStatus(
                            rs.getString("status"));

                    attendanceList.add(attendance);
                }
            }
        }

        return attendanceList;
    }

    public boolean markAttendance(
            int studentId,
            LocalDate attendanceDate,
            String status) throws SQLException {

        if (attendanceDate == null) {
            throw new IllegalArgumentException(
                    "Attendance date is required.");
        }

        if (!"PRESENT".equalsIgnoreCase(status)
                && !"ABSENT".equalsIgnoreCase(status)) {
            throw new IllegalArgumentException(
                    "Status must be PRESENT or ABSENT.");
        }

        String sql = """
                INSERT INTO attendance
                    (student_id, attendance_date, status)
                VALUES (?, ?, ?)
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, studentId);
            statement.setDate(
                    2, Date.valueOf(attendanceDate));
            statement.setString(
                    3, status.toUpperCase());

            return statement.executeUpdate() == 1;
        }
    }

    public List<Attendance> getAllAttendance() throws SQLException {

        List<Attendance> attendanceList = new ArrayList<>();

        String sql = """
                SELECT id, student_id, attendance_date, status
                FROM attendance
                ORDER BY attendance_date DESC
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                Attendance attendance = new Attendance();

                attendance.setId(rs.getInt("id"));
                attendance.setStudentId(rs.getInt("student_id"));

                Date date = rs.getDate("attendance_date");
                if (date != null) {
                    attendance.setAttendanceDate(date.toLocalDate());
                }

                attendance.setStatus(rs.getString("status"));
                attendanceList.add(attendance);
            }
        }

        return attendanceList;
    }
}