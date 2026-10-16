<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard | LBRCE Hostel Management</title>
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
        <a href="${pageContext.request.contextPath}/admin/wardens">Wardens</a>
        <a href="${pageContext.request.contextPath}/admin/fees">Fees</a>
        <a href="${pageContext.request.contextPath}/admin/complaints">Complaints</a>
        <a href="${pageContext.request.contextPath}/admin/outings">Outings</a>
        <a href="${pageContext.request.contextPath}/admin/duty-shifts">Shifts</a>
        <a href="${pageContext.request.contextPath}/admin/mess-bills">Mess Bills</a>
        <a href="${pageContext.request.contextPath}/admin/attendance">Attendance</a>
        <a href="${pageContext.request.contextPath}/admin/reports">Reports</a>
        <a href="${pageContext.request.contextPath}/notices">Notices</a>

        <form action="${pageContext.request.contextPath}/logout" method="post" class="inline-form">
            <button type="submit" class="btn btn-secondary">Logout</button>
        </form>
    </nav>
</header>

<main class="container">

    <section class="page-heading">
        <h1>Welcome, <c:out value="${sessionScope.username}" default="Admin"/>!</h1>
        <p>Administrator Control Center &mdash; Manage hostel residents, staff, facilities, and records.</p>
    </section>

    <!-- Stat Metric Cards -->
    <div class="stats-grid" style="display: grid; grid-template-columns: repeat(auto-fit, minmax(180px, 1fr)); gap: 18px; margin-bottom: 30px;">
        <div class="card stat-card" style="text-align: center; padding: 20px;">
            <div style="font-size: 32px; font-weight: bold; color: var(--primary);"><c:out value="${studentCount}" default="0"/></div>
            <div style="color: var(--muted); font-size: 14px; margin-top: 5px;">Total Students</div>
        </div>

        <div class="card stat-card" style="text-align: center; padding: 20px;">
            <div style="font-size: 32px; font-weight: bold; color: var(--primary);"><c:out value="${roomCount}" default="0"/></div>
            <div style="color: var(--muted); font-size: 14px; margin-top: 5px;">Hostel Rooms</div>
        </div>

        <div class="card stat-card" style="text-align: center; padding: 20px;">
            <div style="font-size: 32px; font-weight: bold; color: #0284c7;"><c:out value="${wardenCount}" default="0"/></div>
            <div style="color: var(--muted); font-size: 14px; margin-top: 5px;">Wardens</div>
        </div>

        <div class="card stat-card" style="text-align: center; padding: 20px;">
            <div style="font-size: 32px; font-weight: bold; color: #d97706;"><c:out value="${complaintCount}" default="0"/></div>
            <div style="color: var(--muted); font-size: 14px; margin-top: 5px;">Active Complaints</div>
        </div>

        <div class="card stat-card" style="text-align: center; padding: 20px;">
            <div style="font-size: 32px; font-weight: bold; color: #2563eb;"><c:out value="${outingCount}" default="0"/></div>
            <div style="color: var(--muted); font-size: 14px; margin-top: 5px;">Outing Requests</div>
        </div>

        <div class="card stat-card" style="text-align: center; padding: 20px;">
            <div style="font-size: 32px; font-weight: bold; color: #16a34a;"><c:out value="${noticeCount}" default="0"/></div>
            <div style="color: var(--muted); font-size: 14px; margin-top: 5px;">Active Notices</div>
        </div>
    </div>

    <!-- Quick Navigation Grid -->
    <section class="dashboard-grid">

        <article class="card dashboard-card">
            <h2>Student Management</h2>
            <p>View registered student details, room allocations, branch, and contact information.</p>
            <a href="${pageContext.request.contextPath}/admin/students" class="btn btn-primary">
                Manage Students
            </a>
        </article>

        <article class="card dashboard-card">
            <h2>Room Allocations</h2>
            <p>Manage hostel rooms, monitor capacity, bed allocations, and availability status.</p>
            <a href="${pageContext.request.contextPath}/admin/rooms" class="btn btn-primary">
                Manage Rooms
            </a>
        </article>

        <article class="card dashboard-card">
            <h2>Warden Management</h2>
            <p>View hostel warden profiles, contact phone numbers, and designated blocks.</p>
            <a href="${pageContext.request.contextPath}/admin/wardens" class="btn btn-primary">
                Manage Wardens
            </a>
        </article>

        <article class="card dashboard-card">
            <h2>Hostel Fee Records</h2>
            <p>Track student hostel accommodation fee payments, receipts, and pending dues.</p>
            <a href="${pageContext.request.contextPath}/admin/fees" class="btn btn-primary">
                Manage Fees
            </a>
        </article>

        <article class="card dashboard-card">
            <h2>Complaint Resolution</h2>
            <p>Review student maintenance issues and complaints with real-time status.</p>
            <a href="${pageContext.request.contextPath}/admin/complaints" class="btn btn-primary">
                Review Complaints
            </a>
        </article>

        <article class="card dashboard-card">
            <h2>Outing Permissions</h2>
            <p>Review student gate passes, outing reasons, and departure-return timestamps.</p>
            <a href="${pageContext.request.contextPath}/admin/outings" class="btn btn-primary">
                Review Outings
            </a>
        </article>

        <article class="card dashboard-card">
            <h2>Warden Duty Shifts</h2>
            <p>Review and schedule duty shifts, timings, and supervision rotations.</p>
            <a href="${pageContext.request.contextPath}/admin/duty-shifts" class="btn btn-primary">
                Manage Shifts
            </a>
        </article>

        <article class="card dashboard-card">
            <h2>Mess Billing</h2>
            <p>View student mess charges, monthly dining invoices, and payment statuses.</p>
            <a href="${pageContext.request.contextPath}/admin/mess-bills" class="btn btn-primary">
                View Mess Bills
            </a>
        </article>

        <article class="card dashboard-card">
            <h2>Attendance Records</h2>
            <p>Audit daily student hostel attendance logs and absence patterns.</p>
            <a href="${pageContext.request.contextPath}/admin/attendance" class="btn btn-primary">
                View Attendance
            </a>
        </article>

        <article class="card dashboard-card">
            <h2>Analytics &amp; Reports</h2>
            <p>Generate comprehensive hostel statistics, occupancy summaries, and records.</p>
            <a href="${pageContext.request.contextPath}/admin/reports" class="btn btn-primary">
                View Reports
            </a>
        </article>

        <article class="card dashboard-card">
            <h2>Hostel Notices</h2>
            <p>Publish, update, and manage official notices and announcements.</p>
            <a href="${pageContext.request.contextPath}/notices" class="btn btn-primary">
                Manage Notices
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
