<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Warden Dashboard | LBRCE Hostel Management</title>
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

        <form action="${pageContext.request.contextPath}/logout" method="post" class="inline-form">
            <button type="submit" class="btn btn-secondary">Logout</button>
        </form>
    </nav>
</header>

<main class="container">

    <section class="page-heading">
        <h1>Welcome, <c:out value="${warden.fullName}" default="Warden"/>!</h1>
        <p>Warden Control Panel &mdash; Monitor hostel residents, shifts, complaints, and requests.</p>
    </section>

    <!-- Stat Metric Cards -->
    <div class="stats-grid" style="display: grid; grid-template-columns: repeat(auto-fit, minmax(200px, 1fr)); gap: 20px; margin-bottom: 30px;">
        <div class="card stat-card" style="text-align: center; padding: 20px;">
            <div style="font-size: 32px; font-weight: bold; color: var(--primary);"><c:out value="${studentCount}" default="0"/></div>
            <div style="color: var(--muted); font-size: 14px; margin-top: 5px;">Total Students</div>
        </div>

        <div class="card stat-card" style="text-align: center; padding: 20px;">
            <div style="font-size: 32px; font-weight: bold; color: var(--primary);"><c:out value="${roomCount}" default="0"/></div>
            <div style="color: var(--muted); font-size: 14px; margin-top: 5px;">Hostel Rooms</div>
        </div>

        <div class="card stat-card" style="text-align: center; padding: 20px;">
            <div style="font-size: 32px; font-weight: bold; color: #d97706;"><c:out value="${complaintCount}" default="0"/></div>
            <div style="color: var(--muted); font-size: 14px; margin-top: 5px;">Complaints</div>
        </div>

        <div class="card stat-card" style="text-align: center; padding: 20px;">
            <div style="font-size: 32px; font-weight: bold; color: #2563eb;"><c:out value="${outingCount}" default="0"/></div>
            <div style="color: var(--muted); font-size: 14px; margin-top: 5px;">Outing Requests</div>
        </div>

        <div class="card stat-card" style="text-align: center; padding: 20px;">
            <div style="font-size: 32px; font-weight: bold; color: #16a34a;"><c:out value="${shiftCount}" default="0"/></div>
            <div style="color: var(--muted); font-size: 14px; margin-top: 5px;">Assigned Shifts</div>
        </div>
    </div>

    <!-- Quick Navigation Grid -->
    <section class="dashboard-grid">

        <article class="card dashboard-card">
            <h2>Student Directory</h2>
            <p>View registered students, their contact numbers, and assigned rooms.</p>
            <a href="${pageContext.request.contextPath}/warden/students" class="btn btn-primary">
                View Students
            </a>
        </article>

        <article class="card dashboard-card">
            <h2>Room Status</h2>
            <p>Monitor room capacity, current occupancy, and available bed allocations.</p>
            <a href="${pageContext.request.contextPath}/warden/rooms" class="btn btn-primary">
                View Rooms
            </a>
        </article>

        <article class="card dashboard-card">
            <h2>Daily Attendance</h2>
            <p>Check daily roll call attendance and verify student presence records.</p>
            <a href="${pageContext.request.contextPath}/warden/attendance" class="btn btn-primary">
                Check Attendance
            </a>
        </article>

        <article class="card dashboard-card">
            <h2>Duty Schedule</h2>
            <p>Check upcoming assigned duty shifts, dates, and work timings.</p>
            <a href="${pageContext.request.contextPath}/warden/duty-schedule" class="btn btn-primary">
                View Duty Shifts
            </a>
        </article>

        <article class="card dashboard-card">
            <h2>Student Complaints</h2>
            <p>Track maintenance and hostel living complaints filed by students.</p>
            <a href="${pageContext.request.contextPath}/warden/complaints" class="btn btn-primary">
                Review Complaints
            </a>
        </article>

        <article class="card dashboard-card">
            <h2>Outing Requests</h2>
            <p>Review student gate pass and outstation outing permission requests.</p>
            <a href="${pageContext.request.contextPath}/warden/outings" class="btn btn-primary">
                Manage Outings
            </a>
        </article>

        <article class="card dashboard-card">
            <h2>Mess Menu</h2>
            <p>View weekly and daily food menus prepared for hostel dining.</p>
            <a href="${pageContext.request.contextPath}/warden/mess-menu" class="btn btn-primary">
                View Menu
            </a>
        </article>

        <article class="card dashboard-card">
            <h2>Hostel Notices</h2>
            <p>View campus announcements and circulars for hostel students.</p>
            <a href="${pageContext.request.contextPath}/notices" class="btn btn-primary">
                View Notices
            </a>
        </article>

    </section>

</main>

<footer class="footer">
    <p>&copy; LBRCE Hostel Management System. All rights reserved.</p>
</footer>

<script src="${pageContext.request.contextPath}/assets/js/script.js"></script>

</body>
</html>
