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
    <title>Title</title>
</head>
<body>


<h1>Hello <%= session.getAttribute(Constants.USER_COOKIE) %>
</h1>
<h2>Role: <%= session.getAttribute(Constants.ROLE_COOKIE) %>
</h2>

<%
    if (session.getAttribute(Constants.ROLE_COOKIE).equals("ADMIN")) {
        out.print("<a href = \"deleteAcc.jsp\" > Delete a user</a >");
    }
%>
<a href="changePass.jsp">Change password</a>
<a href="Logout">Logout</a>
</body>
</html>

