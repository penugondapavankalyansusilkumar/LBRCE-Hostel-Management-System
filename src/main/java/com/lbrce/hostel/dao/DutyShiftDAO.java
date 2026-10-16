package com.lbrce.hostel.dao;

import com.lbrce.hostel.model.DutyShift;
import com.lbrce.hostel.util.DBConnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class DutyShiftDAO {

    // Get all warden duty shifts
    public List<DutyShift> getAllDutyShifts()
            throws SQLException {

        List<DutyShift> shifts = new ArrayList<>();

        String sql = """
                SELECT id, warden_id, duty_date,
                       start_time, end_time, shift_name
                FROM duty_shifts
                ORDER BY duty_date DESC, start_time
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                shifts.add(mapDutyShift(rs));
            }
        }

        return shifts;
    }

    // Get duty shifts assigned to one warden
    public List<DutyShift> getShiftsByWardenId(int wardenId)
            throws SQLException {

        List<DutyShift> shifts = new ArrayList<>();

        String sql = """
                SELECT id, warden_id, duty_date,
                       start_time, end_time, shift_name
                FROM duty_shifts
                WHERE warden_id = ?
                ORDER BY duty_date DESC, start_time
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, wardenId);

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    shifts.add(mapDutyShift(rs));
                }
            }
        }

        return shifts;
    }

    // Assign a duty shift to a warden
    public boolean addDutyShift(
            int wardenId,
            Date dutyDate,
            Time startTime,
            Time endTime,
            String shiftName) throws SQLException {

        if (dutyDate == null || startTime == null
                || endTime == null
                || shiftName == null || shiftName.isBlank()) {
            throw new IllegalArgumentException(
                    "All duty shift details are required.");
        }

        if (!startTime.before(endTime)) {
            throw new IllegalArgumentException(
                    "End time must be later than start time.");
        }

        String sql = """
                INSERT INTO duty_shifts
                    (warden_id, duty_date, start_time,
                     end_time, shift_name)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, wardenId);
            statement.setDate(2, dutyDate);
            statement.setTime(3, startTime);
            statement.setTime(4, endTime);
            statement.setString(5, shiftName.trim());

            return statement.executeUpdate() == 1;
        }
    }

    // Delete a duty shift
    public boolean deleteDutyShift(int shiftId)
            throws SQLException {

        String sql = """
                DELETE FROM duty_shifts
                WHERE id = ?
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, shiftId);

            return statement.executeUpdate() == 1;
        }
    }

    // Convert a database row into a DutyShift object
    private DutyShift mapDutyShift(ResultSet rs)
            throws SQLException {

        DutyShift shift = new DutyShift();

        shift.setId(rs.getInt("id"));
        shift.setWardenId(rs.getInt("warden_id"));

        Date dutyDate = rs.getDate("duty_date");
        if (dutyDate != null) {
            shift.setDutyDate(dutyDate.toLocalDate());
        }

        Time startTime = rs.getTime("start_time");
        if (startTime != null) {
            shift.setStartTime(startTime.toLocalTime());
        }

        Time endTime = rs.getTime("end_time");
        if (endTime != null) {
            shift.setEndTime(endTime.toLocalTime());
        }

        shift.setShiftName(rs.getString("shift_name"));

        return shift;
    }
}