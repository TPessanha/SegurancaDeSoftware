<%@ page import="Util.Constants" %>
<%
    if (session.getAttribute(Constants.USER_COOKIE) == null) {
        String redirectURL = "login.html";
        response.sendRedirect(redirectURL);
    }
%>
<html lang="en">
<head>
    <title>Register form</title>
</head>
<body>
<h1>Register</h1>

<form method="post" action="register">
    Username:<input type="text" name="user"/><br/>
    Password:<input type="password" name="pass1"/><br/>
    Repeated Password:<input type="password" name="pass2"/><br/>
    <input type="submit" value="register"/>
</form>
<a href="welcome.jsp">Back</a>
</body>
</html>
