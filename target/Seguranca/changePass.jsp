<%@ page import="Util.Constants" %>
<%
    if (session.getAttribute(Constants.USER_COOKIE) == null) {
        String redirectURL = "login.html";
        response.sendRedirect(redirectURL);
    }
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Change Password</title>
</head>
<body>
<h1>Change Password</h1>
<form method="post" action="ChangePassword">
    Password:<input type="password" name="pass1"/><br/>
    Repeated Password:<input type="password" name="pass2"/><br/>
    <input type="submit" value="Change Password"/>
</form>
<a href="welcome.jsp">Back</a>
</body>
</html>
