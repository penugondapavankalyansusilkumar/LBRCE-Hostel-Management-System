<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Hostel Rooms | LBRCE ERP</title>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>

<header class="topbar">
    <div class="brand">LBRCE <span>Hostel Management</span></div>
    <nav>
        <a href="${pageContext.request.contextPath}/warden/dashboard">Dashboard</a>
        <a href="${pageContext.request.contextPath}/notices">Notices</a>
        <form action="${pageContext.request.contextPath}/logout"
              method="post" class="inline-form">
            <button type="submit" class="btn btn-secondary">Logout</button>
        </form>
    </nav>
</header>

<main class="container">
    <div class="page-heading">
        <h1>Hostel Room Details</h1>
        <p>View room availability and occupancy.</p>
    </div>

    <section class="card">
        <div class="table-wrapper">
            <table>
                <thead>
                <tr>
                    <th>Room Number</th>
                    <th>Room Type</th>
                    <th>Capacity</th>
                    <th>Occupied</th>
                    <th>Monthly Fee</th>
                    <th>Status</th>
                </tr>
                </thead>

                <tbody>
                <c:forEach var="room" items="${rooms}">
                    <tr>
                        <td><c:out value="${room.roomNumber}"/></td>
                        <td><c:out value="${room.roomType}"/></td>
                        <td><c:out value="${room.capacity}"/></td>
                        <td><c:out value="${room.occupied}"/></td>
                        <td>₹<c:out value="${room.monthlyFee}"/></td>
                        <td>
                            <span class="status-badge">
                                <c:out value="${room.status}"/>
                            </span>
                        </td>
                    </tr>
                </c:forEach>

                <c:if test="${empty rooms}">
                    <tr>
                        <td colspan="6">No room information available.</td>
                    </tr>
                </c:if>
                </tbody>
            </table>
        </div>
    </section>
</main>

<footer class="footer">
    LBRCE Hostel Management System
</footer>

<script src="${pageContext.request.contextPath}/assets/js/script.js"></script>
</body>
</html>