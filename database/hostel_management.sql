-- =========================================================
-- LBRCE HOSTEL MANAGEMENT SYSTEM
-- Database: hostel_management
-- Authoritative Schema & Seed Data
-- =========================================================

CREATE DATABASE IF NOT EXISTS hostel_management;
USE hostel_management;

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS bills, leave_requests, meal_attendance, payments, room_allocations;
DROP TABLE IF EXISTS duty_shifts, outings, notices, mess_menu, mess_bills, fees, complaints, attendance, wardens, students, rooms, users;

SET FOREIGN_KEY_CHECKS = 1;

-- =========================================================
-- 1. USERS TABLE
-- =========================================================
CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(20) NOT NULL DEFAULT 'STUDENT',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =========================================================
-- 2. ROOMS TABLE
-- =========================================
CREATE TABLE rooms (
    id INT PRIMARY KEY AUTO_INCREMENT,
    room_number VARCHAR(20) NOT NULL UNIQUE,
    room_type VARCHAR(30) DEFAULT 'Regular',
    capacity INT NOT NULL,
    occupied INT DEFAULT 0,
    monthly_fee DECIMAL(10, 2) NOT NULL DEFAULT 5000.00,
    status VARCHAR(30) DEFAULT 'AVAILABLE'
);

-- =========================================================
-- 3. STUDENTS TABLE
-- =========================================================
CREATE TABLE students (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT UNIQUE,
    roll_number VARCHAR(30) NOT NULL UNIQUE,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    phone VARCHAR(20),
    gender VARCHAR(10),
    room_id INT,
    course VARCHAR(50),
    year INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE SET NULL,
    FOREIGN KEY (room_id) REFERENCES rooms(id) ON DELETE SET NULL
);

-- =========================================================
-- 4. WARDENS TABLE
-- =========================================================
CREATE TABLE wardens (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT UNIQUE,
    full_name VARCHAR(100) NOT NULL,
    phone VARCHAR(20),
    email VARCHAR(100),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE SET NULL
);

-- =========================================================
-- 5. ATTENDANCE TABLE
-- =========================================================
CREATE TABLE attendance (
    id INT PRIMARY KEY AUTO_INCREMENT,
    student_id INT NOT NULL,
    attendance_date DATE NOT NULL,
    status VARCHAR(20) NOT NULL,
    FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE
);

-- =========================================================
-- 6. COMPLAINTS TABLE
-- =========================================================
CREATE TABLE complaints (
    id INT PRIMARY KEY AUTO_INCREMENT,
    student_id INT NOT NULL,
    subject VARCHAR(150) NOT NULL,
    description TEXT NOT NULL,
    status VARCHAR(30) DEFAULT 'PENDING',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE
);

-- =========================================================
-- 7. FEES TABLE
-- =========================================================
CREATE TABLE fees (
    id INT PRIMARY KEY AUTO_INCREMENT,
    student_id INT NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    paid_amount DECIMAL(10, 2) DEFAULT 0.00,
    due_date DATE,
    status VARCHAR(20) DEFAULT 'PENDING',
    FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE
);

-- =========================================================
-- 8. MESS BILLS TABLE
-- =========================================================
CREATE TABLE mess_bills (
    id INT PRIMARY KEY AUTO_INCREMENT,
    student_id INT NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    billing_date DATE NOT NULL,
    status VARCHAR(20) DEFAULT 'PENDING',
    FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE
);

-- =========================================================
-- 9. MESS MENU TABLE
-- =========================================================
CREATE TABLE mess_menu (
    id INT PRIMARY KEY AUTO_INCREMENT,
    meal_type VARCHAR(30) NOT NULL,
    menu_items TEXT NOT NULL,
    menu_date DATE NOT NULL
);

-- =========================================================
-- 10. NOTICES TABLE
-- =========================================================
CREATE TABLE notices (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(150) NOT NULL,
    content TEXT NOT NULL,
    created_by INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (created_by) REFERENCES users(id) ON DELETE SET NULL
);

-- =========================================================
-- 11. OUTINGS TABLE
-- =========================================================
CREATE TABLE outings (
    id INT PRIMARY KEY AUTO_INCREMENT,
    student_id INT NOT NULL,
    reason VARCHAR(255) NOT NULL,
    outing_date DATE NOT NULL,
    expected_return_date DATE NOT NULL,
    status VARCHAR(30) DEFAULT 'PENDING',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE
);

-- =========================================================
-- 12. DUTY SHIFTS TABLE
-- =========================================================
CREATE TABLE duty_shifts (
    id INT PRIMARY KEY AUTO_INCREMENT,
    warden_id INT NOT NULL,
    duty_date DATE NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    shift_name VARCHAR(50) NOT NULL,
    FOREIGN KEY (warden_id) REFERENCES wardens(id) ON DELETE CASCADE
);

-- =========================================================
-- SEED DATA
-- =========================================================

-- USERS
INSERT INTO users (id, username, password, role) VALUES
(1, 'admin', 'admin123', 'ADMIN'),
(2, 'warden', 'warden123', 'WARDEN'),
(3, 'student', 'student123', 'STUDENT'),
(4, 'student1', 'student123', 'STUDENT'),
(5, 'student2', 'student123', 'STUDENT'),
(6, 'student3', 'student123', 'STUDENT');

-- ROOMS
INSERT INTO rooms (id, room_number, room_type, capacity, occupied, monthly_fee, status) VALUES
(1, 'A101', 'AC', 3, 2, 8000.00, 'AVAILABLE'),
(2, 'A102', 'NON-AC', 4, 1, 5000.00, 'AVAILABLE'),
(3, 'A103', 'AC', 2, 1, 8000.00, 'AVAILABLE'),
(4, 'B101', 'NON-AC', 4, 0, 5000.00, 'AVAILABLE'),
(5, 'B102', 'AC', 3, 0, 8000.00, 'AVAILABLE');

-- WARDENS
INSERT INTO wardens (id, user_id, full_name, phone, email) VALUES
(1, 2, 'Dr. K. Srinivasa Rao', '9848012345', 'warden@lbrce.ac.in');

-- STUDENTS
INSERT INTO students (id, user_id, roll_number, full_name, email, phone, gender, room_id, course, year) VALUES
(1, 3, '24761A0501', 'K. Rajesh', 'student@lbrce.ac.in', '9876543210', 'Male', 1, 'B.Tech CSE', 3),
(2, 4, '24761A0502', 'P. Sai Kumar', 'saikumar@lbrce.ac.in', '9876543211', 'Male', 1, 'B.Tech CSE', 3),
(3, 5, '24761A0503', 'V. Sneha', 'sneha@lbrce.ac.in', '9876543212', 'Female', 2, 'B.Tech IT', 2),
(4, 6, '24761A0504', 'M. Tarun', 'tarun@lbrce.ac.in', '9876543213', 'Male', 3, 'B.Tech ECE', 4);

-- ATTENDANCE
INSERT INTO attendance (student_id, attendance_date, status) VALUES
(1, CURRENT_DATE, 'PRESENT'),
(2, CURRENT_DATE, 'PRESENT'),
(3, CURRENT_DATE, 'PRESENT'),
(4, CURRENT_DATE, 'ABSENT'),
(1, DATE_SUB(CURRENT_DATE, INTERVAL 1 DAY), 'PRESENT'),
(2, DATE_SUB(CURRENT_DATE, INTERVAL 1 DAY), 'ABSENT');

-- COMPLAINTS
INSERT INTO complaints (student_id, subject, description, status, created_at) VALUES
(1, 'Ceiling Fan Not Working', 'The ceiling fan in room A101 is making loud noise and rotating very slowly.', 'PENDING', NOW()),
(2, 'Hot Water Issue', 'Solar heater water supply is irregular on 1st floor bathrooms.', 'IN_PROGRESS', NOW()),
(3, 'Wi-Fi Connectivity', 'Signal strength in block A room 102 is very weak.', 'RESOLVED', NOW());

-- FEES
INSERT INTO fees (student_id, amount, paid_amount, due_date, status) VALUES
(1, 8000.00, 4000.00, DATE_ADD(CURRENT_DATE, INTERVAL 15 DAY), 'PARTIAL'),
(2, 8000.00, 8000.00, DATE_ADD(CURRENT_DATE, INTERVAL 15 DAY), 'PAID'),
(3, 5000.00, 0.00, DATE_ADD(CURRENT_DATE, INTERVAL 10 DAY), 'PENDING'),
(4, 8000.00, 8000.00, DATE_ADD(CURRENT_DATE, INTERVAL 15 DAY), 'PAID');

-- MESS BILLS
INSERT INTO mess_bills (student_id, amount, billing_date, status) VALUES
(1, 3500.00, CURRENT_DATE, 'PENDING'),
(2, 3500.00, CURRENT_DATE, 'PAID'),
(3, 3500.00, CURRENT_DATE, 'PENDING'),
(4, 3500.00, CURRENT_DATE, 'PAID');

-- MESS MENU
INSERT INTO mess_menu (meal_type, menu_items, menu_date) VALUES
('BREAKFAST', 'Idli, Medu Vada, Sambar, Coconut Chutney, Tea/Coffee', CURRENT_DATE),
('LUNCH', 'Steamed Rice, Dal Tadka, Paneer Butter Masala, Sambar, Curd, Papad', CURRENT_DATE),
('SNACKS', 'Onion Pakoda, Masala Chai / Coffee', CURRENT_DATE),
('DINNER', 'Phulka Roti, Mixed Veg Curry, Jeera Rice, Rasam, Gulab Jamun', CURRENT_DATE),
('BREAKFAST', 'Puri Bhaji, Upma, Chutney, Tea/Coffee', DATE_ADD(CURRENT_DATE, INTERVAL 1 DAY)),
('LUNCH', 'Rice, Tomato Dal, Aloo Fry, Rasam, Curd', DATE_ADD(CURRENT_DATE, INTERVAL 1 DAY));

-- NOTICES
INSERT INTO notices (title, content, created_by, created_at) VALUES
('Hostel Committee Monthly Meeting', 'All floor representatives and mess committee members are requested to attend the monthly review meeting at the Seminar Hall on Saturday at 5:00 PM.', 1, NOW()),
('Mess Timings Reminder', 'Breakfast: 7:30 AM - 9:00 AM | Lunch: 12:30 PM - 2:00 PM | Snacks: 5:00 PM - 6:00 PM | Dinner: 7:30 PM - 9:00 PM. Please adhere strictly to the schedule.', 1, NOW()),
('Campus Placement Drive Visitors', 'Students with visiting parents or guardians must obtain gate passes from the Warden office in advance.', 1, NOW());

-- OUTINGS
INSERT INTO outings (student_id, reason, outing_date, expected_return_date, status, created_at) VALUES
(1, 'Medical Consultation at City Hospital', CURRENT_DATE, CURRENT_DATE, 'APPROVED', NOW()),
(2, 'Family Function at Hometown', CURRENT_DATE, DATE_ADD(CURRENT_DATE, INTERVAL 2 DAY), 'PENDING', NOW()),
(3, 'Purchasing Books and Study Material', CURRENT_DATE, CURRENT_DATE, 'APPROVED', NOW());

-- DUTY SHIFTS
INSERT INTO duty_shifts (warden_id, duty_date, start_time, end_time, shift_name) VALUES
(1, CURRENT_DATE, '06:00:00', '14:00:00', 'Morning Shift'),
(1, DATE_ADD(CURRENT_DATE, INTERVAL 1 DAY), '14:00:00', '22:00:00', 'Evening Shift'),
(1, DATE_ADD(CURRENT_DATE, INTERVAL 2 DAY), '22:00:00', '06:00:00', 'Night Shift');