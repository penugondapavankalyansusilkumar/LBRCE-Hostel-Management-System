<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Student Attendance | LBRCE ERP</title>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/assets/css/style.css">
</head>

<body>

<header class="topbar">
    <div class="brand">LBRCE <span>Hostel Management</span></div>

    <nav>
        <a href="${pageContext.request.contextPath}/warden/dashboard">Dashboard</a>
        <a href="${pageContext.request.contextPath}/warden/students">Students</a>
        <a href="${pageContext.request.contextPath}/warden/rooms">Rooms</a>
        <a href="${pageContext.request.contextPath}/warden/attendance">Attendance</a>
        <a href="${pageContext.request.contextPath}/warden/duty-schedule">Duty Shifts</a>
        <a href="${pageContext.request.contextPath}/warden/complaints">Complaints</a>
        <a href="${pageContext.request.contextPath}/warden/outings">Outings</a>
        <a href="${pageContext.request.contextPath}/warden/mess-menu">Mess Menu</a>
        <a href="${pageContext.request.contextPath}/notices">Notices</a>

        <form action="${pageContext.request.contextPath}/logout"
              method="post" class="inline-form">
            <button type="submit" class="btn btn-secondary">Logout</button>
        </form>
    </nav>
</header>

<main class="container">

    <div class="page-heading">
        <h1>Student Attendance</h1>
        <p>Review hostel attendance records.</p>
    </div>

    <section class="card">
        <div class="table-wrapper">
            <table>
                <thead>
                <tr>
                    <th>Attendance ID</th>
                    <th>Student ID</th>
                    <th>Date</th>
                    <th>Status</th>
                </tr>
                </thead>

                <tbody>
                <c:forEach var="record" items="${attendanceRecords}">
                    <tr>
                        <td><c:out value="${record.id}"/></td>
                        <td><c:out value="${record.studentId}"/></td>
                        <td><c:out value="${record.attendanceDate}"/></td>
                        <td>
                            <span class="status-badge">
                                <c:out value="${record.status}"/>
                            </span>
                        </td>
                    </tr>
                </c:forEach>

                <c:if test="${empty attendanceRecords}">
                    <tr>
                        <td colspan="4">No attendance records available.</td>
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
