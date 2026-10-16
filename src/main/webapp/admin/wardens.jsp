<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Wardens | LBRCE ERP</title>

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
        <h1>Warden Management</h1>
        <p>View hostel wardens and their contact information.</p>
    </div>

    <section class="card">
        <div class="table-wrapper">
            <table>
                <thead>
                <tr>
                    <th>Warden ID</th>
                    <th>Warden Name</th>
                    <th>Phone</th>
                    <th>Email</th>
                </tr>
                </thead>

                <tbody>
                <c:forEach var="warden" items="${wardens}">
                    <tr>
                        <td><c:out value="${warden.id}"/></td>
                        <td><c:out value="${warden.fullName}"/></td>
                        <td><c:out value="${warden.phone}"/></td>
                        <td><c:out value="${warden.email}"/></td>
                    </tr>
                </c:forEach>

                <c:if test="${empty wardens}">
                    <tr>
                        <td colspan="4">No warden records available.</td>
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