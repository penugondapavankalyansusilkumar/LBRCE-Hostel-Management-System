<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Student Dashboard | LBRCE Hostel Management</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>

<header class="topbar">
    <div class="brand">LBRCE Hostel Management</div>

    <nav>
        <a href="${pageContext.request.contextPath}/student/dashboard">Dashboard</a>
        <a href="${pageContext.request.contextPath}/student/rooms">Rooms</a>
        <a href="${pageContext.request.contextPath}/student/attendance">Attendance</a>
        <a href="${pageContext.request.contextPath}/student/fees">Fees</a>
        <a href="${pageContext.request.contextPath}/student/complaints">Complaints</a>
        <a href="${pageContext.request.contextPath}/student/mess">Mess</a>
        <a href="${pageContext.request.contextPath}/student/outings">Outings</a>
        <a href="${pageContext.request.contextPath}/notices">Notices</a>
    </nav>

    <form action="${pageContext.request.contextPath}/logout" method="post">
        <button type="submit" class="btn btn-secondary">Logout</button>
    </form>
</header>

<main class="container">

    <section class="welcome-section">
        <h1>Welcome, <c:out value="${student.fullName}" default="Student"/>!</h1>
        <p>Manage your hostel information and check your details here.</p>
    </section>

    <section class="dashboard-grid">

        <article class="dashboard-card">
            <h2>My Profile</h2>
            <p>View your student and hostel details.</p>
            <a href="${pageContext.request.contextPath}/student/dashboard"
               class="btn btn-primary">View Profile</a>
        </article>

        <article class="dashboard-card">
            <h2>Room Details</h2>
            <p>Check room availability and room information.</p>
            <a href="${pageContext.request.contextPath}/student/rooms"
               class="btn btn-primary">View Rooms</a>
        </article>

        <article class="dashboard-card">
            <h2>Attendance</h2>
            <p>Review your recorded hostel attendance.</p>
            <a href="${pageContext.request.contextPath}/student/attendance"
               class="btn btn-primary">Check Attendance</a>
        </article>

        <article class="dashboard-card">
            <h2>Fee Details</h2>
            <p>View your hostel fee records and payment status.</p>
            <a href="${pageContext.request.contextPath}/student/fees"
               class="btn btn-primary">View Fees</a>
        </article>

        <article class="dashboard-card">
            <h2>Complaints</h2>
            <p>Submit a complaint and check its status.</p>
            <a href="${pageContext.request.contextPath}/student/complaints"
               class="btn btn-primary">Manage Complaints</a>
        </article>

        <article class="dashboard-card">
            <h2>Mess Information</h2>
            <p>View the mess menu and your mess bills.</p>
            <a href="${pageContext.request.contextPath}/student/mess"
               class="btn btn-primary">View Mess</a>
        </article>

        <article class="dashboard-card">
            <h2>Outing Requests</h2>
            <p>Submit an outing request and check its status.</p>
            <a href="${pageContext.request.contextPath}/student/outings"
               class="btn btn-primary">Manage Outings</a>
        </article>

        <article class="dashboard-card">
            <h2>Hostel Notices</h2>
            <p>Read the latest hostel announcements.</p>
            <a href="${pageContext.request.contextPath}/notices"
               class="btn btn-primary">View Notices</a>
        </article>

    </section>

</main>

<footer class="footer">
    <p>&copy; LBRCE Hostel Management System</p>
</footer>

</body>
</html>