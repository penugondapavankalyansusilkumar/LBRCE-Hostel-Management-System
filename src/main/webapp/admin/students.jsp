<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Manage Students | LBRCE ERP</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/assets/css/style.css">
</head>

<body>

<header class="topbar">
    <div class="brand">LBRCE <span>Hostel Management</span></div>

    <nav>
        <a href="${pageContext.request.contextPath}/admin/dashboard">
            Dashboard
        </a>

        <a href="${pageContext.request.contextPath}/admin/rooms">
            Rooms
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
        <h1>Student Management</h1>
        <p>View registered hostel students and their details.</p>
    </div>

    <section class="card">

        <div class="table-wrapper">
            <table>
                <thead>
                <tr>
                    <th>Roll Number</th>
                    <th>Student Name</th>
                    <th>Email</th>
                    <th>Phone</th>
                    <th>Course</th>
                    <th>Year</th>
                    <th>Room ID</th>
                </tr>
                </thead>

                <tbody>
                <c:forEach var="student" items="${students}">
                    <tr>
                        <td>
                            <c:out value="${student.rollNumber}"/>
                        </td>

                        <td>
                            <c:out value="${student.fullName}"/>
                        </td>

                        <td>
                            <c:out value="${student.email}"/>
                        </td>

                        <td>
                            <c:out value="${student.phone}"/>
                        </td>

                        <td>
                            <c:out value="${student.course}"/>
                        </td>

                        <td>
                            <c:out value="${student.year}"/>
                        </td>

                        <td>
                            <c:out value="${student.roomId}"/>
                        </td>
                    </tr>
                </c:forEach>

                <c:if test="${empty students}">
                    <tr>
                        <td colspan="7">
                            No student records available.
                        </td>
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