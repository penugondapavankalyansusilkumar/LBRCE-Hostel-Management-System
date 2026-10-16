package com.lbrce.hostel.dao;

import com.lbrce.hostel.model.MessBill;
import com.lbrce.hostel.model.MessMenu;
import com.lbrce.hostel.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class MessDAO {

    // Get mess bills for an individual student
    public List<MessBill> getBillsByStudentId(int studentId)
            throws SQLException {

        List<MessBill> bills = new ArrayList<>();

        String sql = """
                SELECT id, student_id, amount, billing_date, status
                FROM mess_bills
                WHERE student_id = ?
                ORDER BY billing_date DESC
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, studentId);

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    bills.add(mapMessBill(rs));
                }
            }
        }

        return bills;
    }

    // Get all mess bills for authorized staff
    public List<MessBill> getAllBills() throws SQLException {

        List<MessBill> bills = new ArrayList<>();

        String sql = """
                SELECT id, student_id, amount, billing_date, status
                FROM mess_bills
                ORDER BY billing_date DESC
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                bills.add(mapMessBill(rs));
            }
        }

        return bills;
    }

    // Get the mess menu for all dates
    public List<MessMenu> getAllMenu() throws SQLException {

        List<MessMenu> menuList = new ArrayList<>();

        String sql = """
                SELECT id, meal_type, menu_items, menu_date
                FROM mess_menu
                ORDER BY menu_date DESC, meal_type
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                MessMenu menu = new MessMenu();

                menu.setId(rs.getInt("id"));
                menu.setMealType(rs.getString("meal_type"));
                menu.setMenuItems(rs.getString("menu_items"));

                Date menuDate = rs.getDate("menu_date");

                if (menuDate != null) {
                    menu.setMenuDate(menuDate.toLocalDate());
                }

                menuList.add(menu);
            }
        }

        return menuList;
    }

    // Add a mess bill
    public boolean addBill(
            int studentId,
            double amount,
            String status) throws SQLException {

        if (amount < 0) {
            throw new IllegalArgumentException(
                    "Mess bill amount cannot be negative.");
        }

        if (status == null
                || !List.of("PENDING", "PAID", "PARTIAL")
                .contains(status.toUpperCase())) {
            throw new IllegalArgumentException(
                    "Invalid mess bill status.");
        }

        String sql = """
                INSERT INTO mess_bills
                    (student_id, amount, billing_date, status)
                VALUES (?, ?, CURRENT_DATE, ?)
                """;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, studentId);
            statement.setDouble(2, amount);
            statement.setString(3, status.toUpperCase());

            return statement.executeUpdate() == 1;
        }
    }

    private MessBill mapMessBill(ResultSet rs)
            throws SQLException {

        MessBill bill = new MessBill();

        bill.setId(rs.getInt("id"));
        bill.setStudentId(rs.getInt("student_id"));
        bill.setAmount(rs.getDouble("amount"));

        Date billingDate = rs.getDate("billing_date");

        if (billingDate != null) {
            bill.setBillingDate(billingDate.toLocalDate());
        }

        bill.setStatus(rs.getString("status"));

        return bill;
    }
}