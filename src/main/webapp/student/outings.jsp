<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Outing Requests | LBRCE Hostel Management</title>

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
        <a href="${pageContext.request.contextPath}/student/outings">Outings</a>
    </nav>

    <form action="${pageContext.request.contextPath}/logout" method="post">
        <button type="submit" class="btn btn-secondary">Logout</button>
    </form>
</header>

<main class="container">

    <section class="page-heading">
        <h1>Outing Requests</h1>
        <p>Submit an outing request and track its approval status.</p>
    </section>

    <c:if test="${not empty param.success}">
        <div class="alert alert-success">
            Your outing request was submitted successfully.
        </div>
    </c:if>

    <c:if test="${not empty param.error}">
        <div class="alert alert-error">
            Unable to submit your request. Please check the details and try again.
        </div>
    </c:if>

    <section class="form-card">
        <h2>New Outing Request</h2>

        <form action="${pageContext.request.contextPath}/student/outings"
              method="post">

            <div class="form-group">
                <label for="reason">Reason for Outing</label>
                <textarea id="reason"
                          name="reason"
                          rows="4"
                          maxlength="500"
                          required
                          placeholder="Enter your reason for going out"></textarea>
            </div>

            <div class="form-group">
                <label for="outingDate">Outing Date</label>
                <input type="date"
                       id="outingDate"
                       name="outingDate"
                       required>
            </div>

            <div class="form-group">
                <label for="expectedReturnDate">Expected Return Date</label>
                <input type="date"
                       id="expectedReturnDate"
                       name="expectedReturnDate"
                       required>
            </div>

            <button type="submit" class="btn btn-primary">
                Submit Request
            </button>
        </form>
    </section>

    <section class="table-card">
        <h2>My Outing History</h2>

        <div class="table-responsive">
            <table>
                <thead>
                <tr>
                    <th>S.No.</th>
                    <th>Reason</th>
                    <th>Outing Date</th>
                    <th>Expected Return</th>
                    <th>Request Date</th>
                    <th>Status</th>
                </tr>
                </thead>

                <tbody>
                <c:choose>
                    <c:when test="${not empty outings}">
                        <c:forEach var="outing"
                                   items="${outings}"
                                   varStatus="loop">
                            <tr>
                                <td><c:out value="${loop.count}"/></td>
                                <td><c:out value="${outing.reason}"/></td>
                                <td><c:out value="${outing.outingDate}"/></td>
                                <td><c:out value="${outing.expectedReturnDate}"/></td>
                                <td><c:out value="${outing.createdAt}"/></td>
                                <td>
                                    <span class="status-badge">
                                        <c:out value="${outing.status}"/>
                                    </span>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:when>

                    <c:otherwise>
                        <tr>
                            <td colspan="6">No outing requests found.</td>
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