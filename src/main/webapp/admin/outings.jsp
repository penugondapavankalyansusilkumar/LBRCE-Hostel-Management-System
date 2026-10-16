<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Outing Requests | LBRCE ERP</title>

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
        <h1>Student Outing Requests</h1>
        <p>Review hostel student outing requests and their status.</p>
    </div>

    <section class="card">
        <div class="table-wrapper">
            <table>
                <thead>
                <tr>
                    <th>Request ID</th>
                    <th>Student ID</th>
                    <th>Reason</th>
                    <th>Outing Date</th>
                    <th>Expected Return</th>
                    <th>Status</th>
                </tr>
                </thead>

                <tbody>
                <c:forEach var="outing" items="${outings}">
                    <tr>
                        <td><c:out value="${outing.id}"/></td>
                        <td><c:out value="${outing.studentId}"/></td>
                        <td><c:out value="${outing.reason}"/></td>
                        <td><c:out value="${outing.outingDate}"/></td>
                        <td><c:out value="${outing.expectedReturnDate}"/></td>
                        <td>
                            <span class="status-badge">
                                <c:out value="${outing.status}"/>
                            </span>
                        </td>
                    </tr>
                </c:forEach>

                <c:if test="${empty outings}">
                    <tr>
                        <td colspan="6">No outing requests available.</td>
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