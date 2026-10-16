<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mess Bills | LBRCE ERP</title>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/assets/css/style.css">
</head>

<body>

<header class="topbar">
    <div class="brand">LBRCE <span>Hostel Management</span></div>

    <nav>
        <a href="${pageContext.request.contextPath}/admin/dashboard">Dashboard</a>
        <a href="${pageContext.request.contextPath}/admin/students">Students</a>
        <a href="${pageContext.request.contextPath}/admin/fees">Hostel Fees</a>

        <form action="${pageContext.request.contextPath}/logout"
              method="post" class="inline-form">
            <button type="submit" class="btn btn-secondary">Logout</button>
        </form>
    </nav>
</header>

<main class="container">

    <div class="page-heading">
        <h1>Mess Billing Records</h1>
        <p>View hostel students' mess bills and payment status.</p>
    </div>

    <section class="card">
        <div class="table-wrapper">
            <table>
                <thead>
                <tr>
                    <th>Bill ID</th>
                    <th>Student ID</th>
                    <th>Amount</th>
                    <th>Billing Date</th>
                    <th>Status</th>
                </tr>
                </thead>

                <tbody>
                <c:forEach var="bill" items="${bills}">
                    <tr>
                        <td><c:out value="${bill.id}"/></td>
                        <td><c:out value="${bill.studentId}"/></td>
                        <td>₹<c:out value="${bill.amount}"/></td>
                        <td><c:out value="${bill.billingDate}"/></td>
                        <td>
                            <span class="status-badge">
                                <c:out value="${bill.status}"/>
                            </span>
                        </td>
                    </tr>
                </c:forEach>

                <c:if test="${empty bills}">
                    <tr>
                        <td colspan="5">No mess bills available.</td>
                    </tr>
                </c:if>
                </tbody>
            </table>
        </div>
    </section>

</main>

<footer class="footer">
    <p>&copy; LBRCE Hostel Management System. All rights reserved.</p>
</footer>

<script src="${pageContext.request.contextPath}/assets/js/script.js"></script>

</body>
</html>
