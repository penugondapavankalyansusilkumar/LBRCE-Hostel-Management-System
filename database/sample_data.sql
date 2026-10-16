
USE hostel_management;

-- =========================================
-- 1. USERS
-- =========================================
INSERT INTO users (username, password, role)
VALUES
('admin', 'admin123', 'ADMIN'),
('warden', 'warden123', 'WARDEN'),
('student1', 'student123', 'STUDENT'),
('student2', 'student123', 'STUDENT'),
('student3', 'student123', 'STUDENT');

-- =========================================
-- 2. ROOMS
-- =========================================
INSERT INTO rooms
(room_number, room_type, capacity, occupied, monthly_fee, status)
VALUES
('A101', 'AC', 2, 1, 8000.00, 'AVAILABLE'),
('A102', 'NON-AC', 3, 1, 5000.00, 'AVAILABLE'),
('A103', 'AC', 2, 1, 8000.00, 'AVAILABLE'),
('B101', 'NON-AC', 3, 0, 5000.00, 'AVAILABLE'),
('B102', 'AC', 2, 0, 8000.00, 'AVAILABLE');

-- =========================================
-- 3. STUDENTS
-- =========================================
INSERT INTO students
(user_id, roll_number, full_name, email, phone, gender, room_id, course, year)
VALUES
(3, '24761A0501', 'Student One', 'student1@example.com',
 '9000000001', 'Male', 1, 'CSE', 2),
(4, '24761A0502', 'Student Two', 'student2@example.com',
 '9000000002', 'Male', 2, 'CSE', 2),
(5, '24761A0503', 'Student Three', 'student3@example.com',
 '9000000003', 'Male', 3, 'CSE', 2);

-- =========================================
-- 4. ATTENDANCE
-- =========================================
INSERT INTO attendance (student_id, attendance_date, status)
VALUES
(1, CURRENT_DATE, 'PRESENT'),
(2, CURRENT_DATE, 'PRESENT'),
(3, CURRENT_DATE, 'ABSENT');

-- =========================================
-- 5. COMPLAINTS
-- =========================================
INSERT INTO complaints
(student_id, subject, description, status, created_at)
VALUES
(1, 'Room Maintenance', 'The room fan needs repair.',
 'PENDING', NOW()),
(2, 'Water Supply', 'Water supply is irregular.',
 'IN_PROGRESS', NOW()),
(3, 'Cleaning', 'Please arrange room cleaning.',
 'RESOLVED', NOW());

-- =========================================
-- 6. FEES
-- =========================================
INSERT INTO fees
(student_id, amount, paid_amount, due_date, status)
VALUES
(1, 8000.00, 4000.00,
 DATE_ADD(CURRENT_DATE, INTERVAL 7 DAY), 'PARTIAL'),
(2, 5000.00, 5000.00,
 DATE_ADD(CURRENT_DATE, INTERVAL 7 DAY), 'PAID'),
(3, 8000.00, 0.00,
 DATE_ADD(CURRENT_DATE, INTERVAL 7 DAY), 'PENDING');

-- =========================================
-- 7. MESS BILLS
-- =========================================
INSERT INTO mess_bills
(student_id, amount, billing_date, status)
VALUES
(1, 3500.00, CURRENT_DATE, 'PENDING'),
(2, 3500.00, CURRENT_DATE, 'PAID'),
(3, 3500.00, CURRENT_DATE, 'PENDING');

-- =========================================
-- 8. WARDENS
-- =========================================
INSERT INTO wardens (user_id, full_name, phone, email)
VALUES
(2, 'Hostel Warden', '9000000010', 'warden@example.com');

-- =========================================
-- 9. WARDEN DUTY SHIFTS
-- =========================================
INSERT INTO duty_shifts
(warden_id, duty_date, start_time, end_time, shift_name)
VALUES
(1, CURRENT_DATE, '06:00:00', '14:00:00', 'Morning'),
(1, DATE_ADD(CURRENT_DATE, INTERVAL 1 DAY),
 '14:00:00', '22:00:00', 'Evening');

-- =========================================
-- 10. STUDENT OUTINGS
-- =========================================
INSERT INTO outings
(student_id, reason, outing_date, expected_return_date, status, created_at)
VALUES
(1, 'Personal work', CURRENT_DATE,
 DATE_ADD(CURRENT_DATE, INTERVAL 1 DAY), 'PENDING', NOW()),
(2, 'Medical appointment', CURRENT_DATE,
 CURRENT_DATE, 'APPROVED', NOW());

-- =========================================
-- 11. NOTICES
-- =========================================
INSERT INTO notices (title, content, created_by, created_at)
VALUES
('Hostel Meeting', 'A hostel meeting will be held tomorrow.', 1, NOW()),
('Mess Timings', 'Please follow the prescribed mess timings.', 1, NOW());

-- =========================================
-- 12. MESS MENU
-- =========================================
INSERT INTO mess_menu (meal_type, menu_items, menu_date)
VALUES
('BREAKFAST', 'Idli, Sambar, Chutney', CURRENT_DATE),
('LUNCH', 'Rice, Dal, Curry, Curd', CURRENT_DATE),
('SNACKS', 'Tea and Biscuits', CURRENT_DATE),
('DINNER', 'Chapati, Curry, Rice', CURRENT_DATE);

-- =========================================
-- END OF SAMPLE DATA
-- =========================================

