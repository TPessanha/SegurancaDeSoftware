<%@ page import="Util.Constants" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    if (session == null || session.getAttribute("USER") == null) {
%>
<jsp:forward page="login.html"/>
<%
    } else {
        request.setAttribute("Uname", session.getAttribute(Constants.USER_COOKIE));
        request.setAttribute("Urole", session.getAttribute(Constants.ROLE_COOKIE));
    }


%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Welcome</title>
</head>
<body>

<h1>Hello <c:out value="${Uname}" escapeXml="true"/></h1>

<h2>Role: <c:out value="${Urole}" escapeXml="true"/></h2>

<%
    if (session != null && session.getAttribute(Constants.ROLE_COOKIE) != null && session.getAttribute(Constants.ROLE_COOKIE).equals("ADMIN")) {
        out.print("<a href = \"deleteAcc.jsp\" > Delete a user</a > | ");
        out.print("<a href = \"register.jsp\" > Register a user</a > | ");
    }
%>
<a href="changePass.jsp">Change password</a> |
<a href="Logout">Logout</a>
</body>
</html>

