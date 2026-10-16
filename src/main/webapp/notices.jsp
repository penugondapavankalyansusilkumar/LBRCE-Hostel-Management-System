<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Hostel Notices | LBRCE</title>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/assets/css/style.css">
</head>

<body>

<header class="topbar">
    <div class="brand">LBRCE <span>Hostel Management</span></div>

    <nav>
        <c:choose>
            <c:when test="${sessionScope.role eq 'ADMIN'}">
                <a href="${pageContext.request.contextPath}/admin/dashboard">Dashboard</a>
                <a href="${pageContext.request.contextPath}/admin/students">Students</a>
                <a href="${pageContext.request.contextPath}/admin/rooms">Rooms</a>
            </c:when>
            <c:when test="${sessionScope.role eq 'WARDEN'}">
                <a href="${pageContext.request.contextPath}/warden/dashboard">Dashboard</a>
                <a href="${pageContext.request.contextPath}/warden/students">Students</a>
                <a href="${pageContext.request.contextPath}/warden/rooms">Rooms</a>
            </c:when>
            <c:otherwise>
                <a href="${pageContext.request.contextPath}/student/dashboard">Dashboard</a>
                <a href="${pageContext.request.contextPath}/student/rooms">Rooms</a>
                <a href="${pageContext.request.contextPath}/student/attendance">Attendance</a>
            </c:otherwise>
        </c:choose>
        <a href="${pageContext.request.contextPath}/notices">Notices</a>

        <form action="${pageContext.request.contextPath}/logout" method="post" class="inline-form">
            <button type="submit" class="btn btn-secondary">Logout</button>
        </form>
    </nav>
</header>

<main class="container">

    <section class="page-heading">
        <h1>Hostel Notices & Announcements</h1>
        <p>Stay informed about hostel events, rules, and important updates.</p>
    </section>

    <c:if test="${param.success eq '1' or param.success eq 'added'}">
        <div class="alert alert-success">
            Notice operation completed successfully.
        </div>
    </c:if>

    <c:if test="${param.error eq 'failed'}">
        <div class="alert alert-error">
            The operation failed. Please try again.
        </div>
    </c:if>

    <!-- Admin-only notice publishing form -->
    <c:if test="${sessionScope.role eq 'ADMIN'}">
        <section class="card form-card">
            <h2>Publish a New Notice</h2>

            <form action="${pageContext.request.contextPath}/notices" method="post">
                <input type="hidden" name="action" value="add">

                <div class="form-group">
                    <label for="title">Notice Title</label>
                    <input type="text"
                           id="title"
                           name="title"
                           maxlength="150"
                           required
                           placeholder="Enter notice title">
                </div>

                <div class="form-group">
                    <label for="content">Notice Details</label>
                    <textarea id="content"
                              name="content"
                              rows="5"
                              maxlength="5000"
                              required
                              placeholder="Enter notice details"></textarea>
                </div>

                <button type="submit" class="btn btn-primary">
                    Publish Notice
                </button>
            </form>
        </section>
    </c:if>

    <!-- Notice list -->
    <section class="notice-list">
        <c:choose>
            <c:when test="${not empty notices}">
                <c:forEach var="notice" items="${notices}">
                    <article class="card notice-card" style="margin-bottom: 20px;">
                        <h2><c:out value="${notice.title}"/></h2>

                        <p class="notice-date" style="color: var(--muted); font-size: 13px;">
                            Published: <c:out value="${notice.createdAt}"/>
                        </p>

                        <div class="notice-content" style="margin: 15px 0;">
                            <c:out value="${notice.content}"/>
                        </div>

                        <c:if test="${sessionScope.role eq 'ADMIN'}">
                            <form action="${pageContext.request.contextPath}/notices"
                                  method="post"
                                  data-confirm="Are you sure you want to delete this notice?">
                                <input type="hidden" name="action" value="delete">
                                <input type="hidden" name="noticeId" value="<c:out value='${notice.id}'/>">
                                <button type="submit" class="btn btn-secondary">
                                    Delete Notice
                                </button>
                            </form>
                        </c:if>
                    </article>
                </c:forEach>
            </c:when>

            <c:otherwise>
                <div class="card">
                    <p>No hostel notices have been published yet.</p>
                </div>
            </c:otherwise>
        </c:choose>
    </section>

</main>

<footer class="footer">
    <p>&copy; LBRCE Hostel Management System</p>
</footer>

<script src="${pageContext.request.contextPath}/assets/js/script.js"></script>

</body>
</html>
