<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Attendance | LBRCE Hostel Management</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>

<header class="topbar">
    <div class="brand">LBRCE Hostel Management</div>

    <nav>
        <a href="${pageContext.request.contextPath}/student/dashboard">Dashboard</a>
        <a href="${pageContext.request.contextPath}/student/attendance">Attendance</a>
        <a href="${pageContext.request.contextPath}/student/fees">Fees</a>
        <a href="${pageContext.request.contextPath}/student/complaints">Complaints</a>
    </nav>

    <form action="${pageContext.request.contextPath}/logout" method="post">
        <button type="submit" class="btn btn-secondary">Logout</button>
    </form>
</header>

<main class="container">

    <section class="page-heading">
        <h1>My Attendance</h1>
        <p>View your recorded attendance details.</p>
    </section>

    <section class="table-card">
        <div class="table-responsive">
            <table>
                <thead>
                <tr>
                    <th>S.No.</th>
                    <th>Date</th>
                    <th>Status</th>
                </tr>
                </thead>

                <tbody>
                <c:choose>
                    <c:when test="${not empty attendanceRecords}">
                        <c:forEach var="record"
                                   items="${attendanceRecords}"
                                   varStatus="loop">
                            <tr>
                                <td><c:out value="${loop.count}"/></td>
                                <td>
                                    <c:out value="${record.attendanceDate}"/>
                                </td>
                                <td>
                                    <span class="status-badge">
                                        <c:out value="${record.status}"/>
                                    </span>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:when>

                    <c:otherwise>
                        <tr>
                            <td colspan="3">No attendance records found.</td>
                        </tr>
                    </c:otherwise>
                </c:choose>
                </tbody>
            </table>
        </div>
    </section>

    <a href="${pageContext.request.contextPath}/student/dashboard"
       class="btn btn-secondary">Back to Dashboard</a>

</main>

<footer class="footer">
    <p>&copy; LBRCE Hostel Management System</p>
</footer>

</body>
</html>