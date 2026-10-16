<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Complaints | LBRCE ERP</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/assets/css/style.css">
</head>

<body>

<header class="topbar">
    <div class="brand">LBRCE <span>Hostel Management</span></div>

    <nav>
        <a href="${pageContext.request.contextPath}/warden/dashboard">
            Dashboard
        </a>

        <a href="${pageContext.request.contextPath}/warden/duty-shifts">
            Duty Shifts
        </a>

        <a href="${pageContext.request.contextPath}/notices">
            Notices
        </a>

        <form action="${pageContext.request.contextPath}/logout"
              method="post" class="inline-form">
            <button type="submit" class="btn btn-secondary">
                Logout
            </button>
        </form>
    </nav>
</header>

<main class="container">

    <div class="page-heading">
        <h1>Student Complaints</h1>
        <p>Review hostel complaints and their current status.</p>
    </div>

    <section class="card">
        <div class="table-wrapper">
            <table>
                <thead>
                <tr>
                    <th>Complaint ID</th>
                    <th>Student ID</th>
                    <th>Subject</th>
                    <th>Description</th>
                    <th>Submitted On</th>
                    <th>Status</th>
                </tr>
                </thead>

                <tbody>
                <c:forEach var="complaint" items="${complaints}">
                    <tr>
                        <td><c:out value="${complaint.id}"/></td>
                        <td><c:out value="${complaint.studentId}"/></td>
                        <td><c:out value="${complaint.subject}"/></td>
                        <td><c:out value="${complaint.description}"/></td>
                        <td><c:out value="${complaint.createdAt}"/></td>
                        <td>
                            <span class="status-badge">
                                <c:out value="${complaint.status}"/>
                            </span>
                        </td>
                    </tr>
                </c:forEach>

                <c:if test="${empty complaints}">
                    <tr>
                        <td colspan="6">No complaints available.</td>
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