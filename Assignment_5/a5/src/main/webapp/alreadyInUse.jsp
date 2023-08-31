<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 11.06.2023
  Time: 23:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<html>
<head>
  <title>Create Account</title>
</head>
<body>
<h1>The Name <%= request.getParameter("username")%> Already In Use </h1>

<p>Please enter another name and password.</p>
<form action="create" method="POST">
  <%--@declare id="username"--%><label for="username">UserName</label>
  User name: <input type="text" name="username"/><br/>
  <%--@declare id="password"--%><label for="password">Password</label>
  Password: <input type="password" name="password"/>
  <button type="submit">Login</button>
</form>
</body>
</html>
