<%@ page import="Util.Constants" %>
<%
    if (session == null || session.getAttribute(Constants.USER_COOKIE) == null) {%>
<jsp:forward page="login.html"/>
<%
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
    <label>
        Username:
        <input type="text" name="user"/>
    </label><br/>
    <input type="submit" value="Delete"/>
</form>
<a href="welcome.jsp">Back</a>
</body>
</html>

