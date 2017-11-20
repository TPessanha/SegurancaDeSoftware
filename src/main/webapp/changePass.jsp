<%@ page import="Util.Constants" %>
<%
    if (session == null || session.getAttribute(Constants.USER_COOKIE) == null) {%>
<jsp:forward page="login.html"/>
<%
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
    <label>
        Current Password:
        <input type="password" name="pass0"/>
    </label><br/>
    <label>
        Password:
        <input type="password" name="pass1"/>
    </label><br/>
    <label>
        Repeated Password:
        <input type="password" name="pass2"/>
    </label><br/>
    <input type="submit" value="Change Password"/>
</form>
<a href="welcome.jsp">Back</a>
</body>
</html>
