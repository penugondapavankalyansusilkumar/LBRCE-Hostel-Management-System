<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mess Menu | LBRCE Hostel Management</title>
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
        <h1>Hostel Mess Menu</h1>
        <p>View the mess menu maintained for hostel students.</p>
    </div>

    <section class="card">
        <h2>Weekly Menu Schedule</h2>

        <c:choose>
            <c:when test="${not empty menu}">
                <div class="table-wrapper">
                    <table>
                        <thead>
                        <tr>
                            <th>Date</th>
                            <th>Meal Type</th>
                            <th>Menu Items</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="item" items="${menu}">
                            <tr>
                                <td><c:out value="${item.menuDate}"/></td>
                                <td><c:out value="${item.mealType}"/></td>
                                <td><c:out value="${item.menuItems}"/></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:when>

            <c:otherwise>
                <p>No mess menu entries are available.</p>
            </c:otherwise>
        </c:choose>
    </section>

    <p style="margin-top: 20px;">
        <a class="btn btn-secondary"
           href="${pageContext.request.contextPath}/warden/dashboard">
            &larr; Back to Dashboard
        </a>
    </p>
</main>

<footer class="footer">
    <p>&copy; LBRCE Hostel Management System. All rights reserved.</p>
</footer>

<script src="${pageContext.request.contextPath}/assets/js/script.js"></script>
</body>
</html>
