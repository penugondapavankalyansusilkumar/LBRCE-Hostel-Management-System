<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Reports | LBRCE Hostel Management</title>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>

<header class="topbar">
    <div class="brand">LBRCE <span>Hostel Management</span></div>
    <nav>
        <a href="${pageContext.request.contextPath}/admin/dashboard">Dashboard</a>
        <a href="${pageContext.request.contextPath}/admin/students">Students</a>
        <a href="${pageContext.request.contextPath}/admin/rooms">Rooms</a>
        <a href="${pageContext.request.contextPath}/admin/fees">Fees</a>
        <a href="${pageContext.request.contextPath}/admin/reports">Reports</a>
        <form action="${pageContext.request.contextPath}/logout"
              method="post" class="inline-form">
            <button type="submit" class="btn btn-secondary">Logout</button>
        </form>
    </nav>
</header>

<main class="container">
    <div class="page-heading">
        <h1>Hostel Reports &amp; Analytics</h1>
        <p>Comprehensive overview of hostel residents, room occupancy, and fee status.</p>
    </div>

    <!-- Metrics Cards -->
    <div class="stats-grid" style="display: grid; grid-template-columns: repeat(auto-fit, minmax(200px, 1fr)); gap: 18px; margin-bottom: 30px;">
        <div class="card stat-card" style="text-align: center; padding: 20px;">
            <div style="font-size: 32px; font-weight: bold; color: var(--primary);"><c:out value="${studentCount}" default="0"/></div>
            <div style="color: var(--muted); font-size: 14px; margin-top: 5px;">Enrolled Students</div>
        </div>

        <div class="card stat-card" style="text-align: center; padding: 20px;">
            <div style="font-size: 32px; font-weight: bold; color: var(--primary);"><c:out value="${roomCount}" default="0"/></div>
            <div style="color: var(--muted); font-size: 14px; margin-top: 5px;">Total Rooms</div>
        </div>

        <div class="card stat-card" style="text-align: center; padding: 20px;">
            <div style="font-size: 32px; font-weight: bold; color: #16a34a;"><c:out value="${feeCount}" default="0"/></div>
            <div style="color: var(--muted); font-size: 14px; margin-top: 5px;">Fee Records</div>
        </div>

        <div class="card stat-card" style="text-align: center; padding: 20px;">
            <div style="font-size: 32px; font-weight: bold; color: #d97706;"><c:out value="${complaintCount}" default="0"/></div>
            <div style="color: var(--muted); font-size: 14px; margin-top: 5px;">Total Complaints</div>
        </div>
    </div>

    <!-- Room Occupancy Report -->
    <section class="card" style="margin-bottom: 24px;">
        <h2>Room Occupancy Overview</h2>
        <div class="table-wrapper">
            <table>
                <thead>
                <tr>
                    <th>Room Number</th>
                    <th>Room Type</th>
                    <th>Capacity</th>
                    <th>Current Occupancy</th>
                    <th>Status</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="room" items="${rooms}">
                    <tr>
                        <td><strong><c:out value="${room.roomNumber}"/></strong></td>
                        <td><c:out value="${room.type}"/></td>
                        <td><c:out value="${room.capacity}"/></td>
                        <td><c:out value="${room.currentOccupancy}"/></td>
                        <td>
                            <span class="status-badge">
                                <c:out value="${room.status}"/>
                            </span>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty rooms}">
                    <tr>
                        <td colspan="5">No room records available.</td>
                    </tr>
                </c:if>
                </tbody>
            </table>
        </div>
    </section>

    <!-- Student Enrolment Summary -->
    <section class="card" style="margin-bottom: 24px;">
        <h2>Student Enrolment Summary</h2>
        <div class="table-wrapper">
            <table>
                <thead>
                <tr>
                    <th>Roll Number</th>
                    <th>Name</th>
                    <th>Course</th>
                    <th>Year</th>
                    <th>Phone</th>
                    <th>Room ID</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="student" items="${students}">
                    <tr>
                        <td><c:out value="${student.rollNumber}"/></td>
                        <td><c:out value="${student.fullName}"/></td>
                        <td><c:out value="${student.course}"/></td>
                        <td><c:out value="${student.year}"/></td>
                        <td><c:out value="${student.phone}"/></td>
                        <td><c:out value="${student.roomId}"/></td>
                    </tr>
                </c:forEach>
                <c:if test="${empty students}">
                    <tr>
                        <td colspan="6">No student records available.</td>
                    </tr>
                </c:if>
                </tbody>
            </table>
        </div>
    </section>

    <!-- Fee Collections Summary -->
    <section class="card" style="margin-bottom: 24px;">
        <h2>Fee Status Summary</h2>
        <div class="table-wrapper">
            <table>
                <thead>
                <tr>
                    <th>Fee ID</th>
                    <th>Student ID</th>
                    <th>Amount</th>
                    <th>Fee Type</th>
                    <th>Due Date</th>
                    <th>Status</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="fee" items="${fees}">
                    <tr>
                        <td><c:out value="${fee.id}"/></td>
                        <td><c:out value="${fee.studentId}"/></td>
                        <td>₹<c:out value="${fee.amount}"/></td>
                        <td><c:out value="${fee.feeType}"/></td>
                        <td><c:out value="${fee.dueDate}"/></td>
                        <td>
                            <span class="status-badge">
                                <c:out value="${fee.status}"/>
                            </span>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty fees}">
                    <tr>
                        <td colspan="6">No fee records available.</td>
                    </tr>
                </c:if>
                </tbody>
            </table>
        </div>
    </section>

</main>

<footer class="footer">
    <p>&copy; LBRCE Hostel Management System. All rights reserved.</p>
</footer>

<script src="${pageContext.request.contextPath}/assets/js/script.js"></script>

</body>
</html>
