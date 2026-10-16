<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Complaints | LBRCE Hostel Management</title>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body>

<header class="topbar">
    <div class="brand">LBRCE Hostel Management</div>

    <nav>
        <a href="${pageContext.request.contextPath}/student/dashboard">Dashboard</a>
        <a href="${pageContext.request.contextPath}/student/attendance">Attendance</a>
        <a href="${pageContext.request.contextPath}/student/fees">Fees</a>
        <a href="${pageContext.request.contextPath}/student/complaints">Complaints</a>
    </nav>

    <form action="${pageContext.request.contextPath}/logout" method="post">
        <button type="submit" class="btn btn-secondary">Logout</button>
    </form>
</header>

<main class="container">

    <section class="page-heading">
        <h1>My Complaints</h1>
        <p>Submit a hostel complaint and track its status.</p>
    </section>

    <c:if test="${not empty param.success}">
        <div class="alert alert-success">Complaint submitted successfully.</div>
    </c:if>

    <c:if test="${not empty param.error}">
        <div class="alert alert-error">Unable to submit the complaint. Please try again.</div>
    </c:if>

    <section class="form-card">
        <h2>Submit a New Complaint</h2>

        <form action="${pageContext.request.contextPath}/student/complaints"
              method="post">

            <div class="form-group">
                <label for="subject">Complaint Subject</label>
                <input type="text"
                       id="subject"
                       name="subject"
                       maxlength="150"
                       required
                       placeholder="e.g. Room maintenance">
            </div>

            <div class="form-group">
                <label for="description">Complaint Description</label>
                <textarea id="description"
                          name="description"
                          rows="5"
                          maxlength="2000"
                          required
                          placeholder="Describe your complaint"></textarea>
            </div>

            <button type="submit" class="btn btn-primary">
                Submit Complaint
            </button>
        </form>
    </section>

    <section class="table-card">
        <h2>Complaint History</h2>

        <div class="table-responsive">
            <table>
                <thead>
                <tr>
                    <th>S.No.</th>
                    <th>Subject</th>
                    <th>Description</th>
                    <th>Date</th>
                    <th>Status</th>
                </tr>
                </thead>

                <tbody>
                <c:choose>
                    <c:when test="${not empty complaints}">
                        <c:forEach var="complaint"
                                   items="${complaints}"
                                   varStatus="loop">
                            <tr>
                                <td><c:out value="${loop.count}"/></td>
                                <td><c:out value="${complaint.subject}"/></td>
                                <td><c:out value="${complaint.description}"/></td>
                                <td><c:out value="${complaint.createdAt}"/></td>
                                <td>
                                    <span class="status-badge">
                                        <c:out value="${complaint.status}"/>
                                    </span>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:when>

                    <c:otherwise>
                        <tr>
                            <td colspan="5">No complaints submitted yet.</td>
                        </tr>
                    </c:otherwise>
                </c:choose>
                </tbody>
            </table>
        </div>
    </section>

    <a href="${pageContext.request.contextPath}/student/dashboard"
       class="btn btn-secondary">Back to Dashboard</a>

</main>

<footer class="footer">
    <p>&copy; LBRCE Hostel Management System</p>
</footer>

</body>
</html>