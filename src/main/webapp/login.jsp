<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>LBRCE Hostel Management | Login</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body class="login-page">

<div class="login-container">
    <div class="login-card">

        <div class="login-header">
            <div class="college-logo">LBRCE</div>
            <h1>Hostel Management System</h1>
            <p>Lakireddy Bali Reddy College of Engineering</p>
        </div>

        <% if (request.getAttribute("error") != null) { %>
            <div class="alert alert-error">
                <%= request.getAttribute("error") %>
            </div>
        <% } %>

        <% if ("1".equals(request.getParameter("logout"))) { %>
            <div class="alert alert-success">
                You have logged out successfully.
            </div>
        <% } %>

        <form action="${pageContext.request.contextPath}/login"
              method="post">

            <div class="form-group">
                <label for="username">Username</label>
                <input
                    type="text"
                    id="username"
                    name="username"
                    placeholder="Enter your username"
                    autocomplete="username"
                    maxlength="100"
                    required>
            </div>

            <div class="form-group">
                <label for="password">Password</label>
                <input
                    type="password"
                    id="password"
                    name="password"
                    placeholder="Enter your password"
                    autocomplete="current-password"
                    required>
            </div>

            <button type="submit" class="btn btn-primary login-button">
                Sign In
            </button>
        </form>

        <div class="login-footer">
            <p>Authorized students, wardens, and administrators only.</p>
            <p>&copy; <%= java.time.Year.now().getValue() %> LBRCE</p>
        </div>

    </div>
</div>

</body>
</html>