<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Hostel Notices | LBRCE ERP</title>
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
        <h1>Hostel Notices</h1>
        <p>View important announcements from the administration.</p>
    </div>

    <section class="card">

        <c:forEach var="notice" items="${notices}">
            <article class="notice-card" style="padding: 16px 0; border-bottom: 1px solid var(--border);">
                <h2 style="margin-top: 0; color: var(--primary);"><c:out value="${notice.title}"/></h2>

                <p><c:out value="${notice.content}"/></p>

                <small style="color: var(--muted);">
                    Published:
                    <c:out value="${notice.createdAt}"/>
                </small>
            </article>
        </c:forEach>

        <c:if test="${empty notices}">
            <p>No notices have been published yet.</p>
        </c:if>

    </section>

</main>

<footer class="footer">
    <p>&copy; LBRCE Hostel Management System. All rights reserved.</p>
</footer>

<script src="${pageContext.request.contextPath}/assets/js/script.js"></script>

</body>
</html>
