<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isErrorPage="true" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Error | LBRCE Hostel Management</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/assets/css/style.css">
</head>
<body class="error-page">

<div class="error-container">
    <div class="error-card">

        <div class="error-icon">!</div>

        <h1>Something Went Wrong</h1>

        <p>
            We couldn't complete your request.
            Please try again later.
        </p>

        <%
            Object errorMessage = request.getAttribute("error");
            if (errorMessage != null) {
        %>
            <div class="alert alert-error">
                <%= org.apache.taglibs.standard.functions.Functions
                        .escapeXml(errorMessage.toString()) %>
            </div>
        <%
            }
        %>

        <a href="<%= request.getContextPath() %>/index.jsp"
           class="btn btn-primary">
            Back to Home
        </a>

        <a href="<%= request.getContextPath() %>/login"
           class="btn btn-secondary">
            Go to Login
        </a>

    </div>
</div>

</body>
</html>