<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 12.06.2023
  Time: 18:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Welcome <%= request.getParameter("username")%></title>
</head>
<body>
  <h1>Welcome <%= request.getParameter("username")%></h1>
</body>
</html>
