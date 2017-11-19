<%@ page import="Util.Constants" %>
<%
if (session.getAttribute(Constants.USER_COOKIE) == null) {
String redirectURL = "login.html";
response.sendRedirect(redirectURL);
}
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Delete Account</title>
</head>
<body>
<h1>Delete Account</h1>
<form method="post" action="DeleteAccount">
    Username:<input type="text" name="user"/><br/>
    <input type="submit" value="Delete"/>
</form>
<a href="welcome.jsp">Back</a>
</body>
</html>

