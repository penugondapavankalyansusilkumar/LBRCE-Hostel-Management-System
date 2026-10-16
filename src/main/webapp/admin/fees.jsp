<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Hostel Fees | LBRCE ERP</title>

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

        <form action="${pageContext.request.contextPath}/logout"
              method="post" class="inline-form">
            <button type="submit" class="btn btn-secondary">Logout</button>
        </form>
    </nav>
</header>

<main class="container">

    <div class="page-heading">
        <h1>Hostel Fee Management</h1>
        <p>Review student fee records and payment status.</p>
    </div>

    <section class="card">
        <div class="table-wrapper">
            <table>
                <thead>
                <tr>
                    <th>Fee ID</th>
                    <th>Student ID</th>
                    <th>Total Fee</th>
                    <th>Amount Paid</th>
                    <th>Balance</th>
                    <th>Due Date</th>
                    <th>Status</th>
                </tr>
                </thead>

                <tbody>
                <c:forEach var="fee" items="${fees}">
                    <tr>
                        <td><c:out value="${fee.id}"/></td>
                        <td><c:out value="${fee.studentId}"/></td>
                        <td>₹<c:out value="${fee.amount}"/></td>
                        <td>₹<c:out value="${fee.paidAmount}"/></td>
                        <td>₹${fee.amount - fee.paidAmount}</td>
                        <td><c:out value="${fee.dueDate}"/></td>
                        <td>
                            <span class="status-badge">
                                <c:out value="${fee.status}"/>
                            </span>
                        </td>
                    </tr>
                </c:forEach>

                <c:if test="${empty fees}">
                    <tr>
                        <td colspan="7">No fee records available.</td>
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