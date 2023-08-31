<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 12.06.2023
  Time: 18:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create Account</title>
</head>
<body>
<h1>Create New Account</h1>

<p>Please enter proposed name or password.</p>
<form action="create" method="POST">
    <%--@declare id="username"--%><label for="username">UserName</label>
    User name: <input type="text" name="username"/><br/>
    <%--@declare id="password"--%><label for="password">Password</label>
    Password: <input type="password" name="password"/>
    <button type="submit">Login</button>
    </form>
</body>
</html>
