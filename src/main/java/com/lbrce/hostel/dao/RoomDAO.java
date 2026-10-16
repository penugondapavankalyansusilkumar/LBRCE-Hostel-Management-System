package com.lbrce.hostel.dao;

import com.lbrce.hostel.model.Room;
import com.lbrce.hostel.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomDAO {

    public List<Room> getAllRooms() throws SQLException {

        List<Room> rooms = new ArrayList<>();

        String sql = """
                SELECT id, room_number, room_type, capacity,
                       occupied, monthly_fee, status
                FROM rooms
                ORDER BY room_number
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                rooms.add(mapRoom(rs));
            }
        }

        return rooms;
    }

    public Room getRoomById(int id) throws SQLException {

        String sql = """
                SELECT id, room_number, room_type, capacity,
                       occupied, monthly_fee, status
                FROM rooms
                WHERE id = ?
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return mapRoom(rs);
                }
            }
        }

        return null;
    }

    public List<Room> getAvailableRooms() throws SQLException {

        List<Room> rooms = new ArrayList<>();

        String sql = """
                SELECT id, room_number, room_type, capacity,
                       occupied, monthly_fee, status
                FROM rooms
                WHERE occupied < capacity
                ORDER BY room_number
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                rooms.add(mapRoom(rs));
            }
        }

        return rooms;
    }

    private Room mapRoom(ResultSet rs) throws SQLException {

        Room room = new Room();

        room.setId(rs.getInt("id"));
        room.setRoomNumber(rs.getString("room_number"));
        room.setRoomType(rs.getString("room_type"));
        room.setCapacity(rs.getInt("capacity"));
        room.setOccupied(rs.getInt("occupied"));
        room.setMonthlyFee(rs.getDouble("monthly_fee"));
        room.setStatus(rs.getString("status"));

        return room;
    }
}