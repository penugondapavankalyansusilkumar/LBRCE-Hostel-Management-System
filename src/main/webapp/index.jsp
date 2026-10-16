<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>LBRCE | Hostel Management System</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body class="home-page">

<header class="main-header">
    <div class="brand">
        <div class="college-logo">LBRCE</div>
        <div>
            <h2>Hostel Management</h2>
            <p>Lakireddy Bali Reddy College of Engineering</p>
        </div>
    </div>

    <nav>
        <a href="${pageContext.request.contextPath}/index.jsp">Home</a>
        <a href="${pageContext.request.contextPath}/login">Login</a>
    </nav>
</header>

<main class="hero">
    <div class="hero-content">
        <span class="eyebrow">LBRCE HOSTEL PORTAL</span>

        <h1>
            Smart Hostel<br>
            <span>Management System</span>
        </h1>

        <p>
            A centralized portal for hostel students, wardens, and
            administrators to manage rooms, attendance, fees,
            mess information, complaints, and outing requests.
        </p>

        <a href="${pageContext.request.contextPath}/login"
           class="btn btn-primary">
            Login to Portal →
        </a>
    </div>

    <div class="feature-grid">

        <div class="feature-card">
            <div class="feature-icon">🏠</div>
            <h3>Room Management</h3>
            <p>View hostel rooms, capacity, and availability.</p>
        </div>

        <div class="feature-card">
            <div class="feature-icon">📋</div>
            <h3>Attendance</h3>
            <p>Access student attendance records.</p>
        </div>

        <div class="feature-card">
            <div class="feature-icon">💳</div>
            <h3>Fee Tracking</h3>
            <p>View hostel fee details and payment status.</p>
        </div>

        <div class="feature-card">
            <div class="feature-icon">🍽️</div>
            <h3>Mess Information</h3>
            <p>Check mess bills and meal menus.</p>
        </div>

    </div>
</main>

<footer class="main-footer">
    <p>&copy; <%= java.time.Year.now().getValue() %>
        LBRCE Hostel Management System. All rights reserved.</p>
</footer>

</body>
</html>