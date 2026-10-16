package com.lbrce.hostel.dao;

import com.lbrce.hostel.model.Student;
import com.lbrce.hostel.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    public Student getStudentById(int id) throws SQLException {

        String sql = """
                SELECT id, user_id, roll_number, full_name,
                       email, phone, gender, room_id, course, year
                FROM students
                WHERE id = ?
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return mapStudent(rs);
                }
            }
        }

        return null;
    }

    public Student getStudentByUserId(int userId)
            throws SQLException {

        String sql = """
                SELECT id, user_id, roll_number, full_name,
                       email, phone, gender, room_id, course, year
                FROM students
                WHERE user_id = ?
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, userId);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return mapStudent(rs);
                }
            }
        }

        return null;
    }

    public List<Student> getAllStudents() throws SQLException {

        List<Student> students = new ArrayList<>();

        String sql = """
                SELECT id, user_id, roll_number, full_name,
                       email, phone, gender, room_id, course, year
                FROM students
                ORDER BY full_name
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                students.add(mapStudent(rs));
            }
        }

        return students;
    }

    private Student mapStudent(ResultSet rs) throws SQLException {

        Student student = new Student();

        student.setId(rs.getInt("id"));
        student.setUserId(rs.getInt("user_id"));
        student.setRollNumber(rs.getString("roll_number"));
        student.setFullName(rs.getString("full_name"));
        student.setEmail(rs.getString("email"));
        student.setPhone(rs.getString("phone"));
        student.setGender(rs.getString("gender"));

        int roomId = rs.getInt("room_id");

        if (!rs.wasNull()) {
            student.setRoomId(roomId);
        }

        student.setCourse(rs.getString("course"));
        student.setYear(rs.getString("year"));

        return student;
    }
}