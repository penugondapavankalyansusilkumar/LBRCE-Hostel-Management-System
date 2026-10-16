<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Profile | LBRCE ERP</title>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/assets/css/style.css">
</head>

<body>

<header class="topbar">
    <div class="brand">LBRCE <span>Hostel Management</span></div>

    <nav>
        <a href="${pageContext.request.contextPath}/student/dashboard">Dashboard</a>
        <a href="${pageContext.request.contextPath}/student/profile">Profile</a>
        <a href="${pageContext.request.contextPath}/student/rooms">Rooms</a>
        <a href="${pageContext.request.contextPath}/student/attendance">Attendance</a>
        <a href="${pageContext.request.contextPath}/student/fees">Fees</a>
        <a href="${pageContext.request.contextPath}/student/complaints">Complaints</a>
        <a href="${pageContext.request.contextPath}/student/mess">Mess</a>
        <a href="${pageContext.request.contextPath}/student/outings">Outings</a>
        <a href="${pageContext.request.contextPath}/notices">Notices</a>

        <form action="${pageContext.request.contextPath}/logout"
              method="post" class="inline-form">
            <button type="submit" class="btn btn-secondary">Logout</button>
        </form>
    </nav>
</header>

<main class="container">

    <div class="page-heading">
        <h1>My Student Profile</h1>
        <p>View your registered hostel information.</p>
    </div>

    <section class="card">

        <c:choose>
            <c:when test="${not empty student}">
                <div class="table-wrapper">
                    <table>
                        <tbody>
                        <tr>
                            <th>Student ID</th>
                            <td><c:out value="${student.id}"/></td>
                        </tr>

                        <tr>
                            <th>Roll Number</th>
                            <td><c:out value="${student.rollNumber}"/></td>
                        </tr>

                        <tr>
                            <th>Full Name</th>
                            <td><c:out value="${student.fullName}"/></td>
                        </tr>

                        <tr>
                            <th>Email</th>
                            <td><c:out value="${student.email}"/></td>
                        </tr>

                        <tr>
                            <th>Phone</th>
                            <td><c:out value="${student.phone}"/></td>
                        </tr>

                        <tr>
                            <th>Course</th>
                            <td><c:out value="${student.course}"/></td>
                        </tr>

                        <tr>
                            <th>Year</th>
                            <td><c:out value="${student.year}"/></td>
                        </tr>

                        <tr>
                            <th>Room ID</th>
                            <td><c:out value="${student.roomId}"/></td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </c:when>

            <c:otherwise>
                <p>Student profile information is not available.</p>
            </c:otherwise>
        </c:choose>

    </section>

</main>

<footer class="footer">
    <p>&copy; LBRCE Hostel Management System. All rights reserved.</p>
</footer>

<script src="${pageContext.request.contextPath}/assets/js/script.js"></script>

</body>
</html>
