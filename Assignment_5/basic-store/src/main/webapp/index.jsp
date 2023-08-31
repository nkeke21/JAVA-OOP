<%@ page import="store.myDatabse" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="store.Product" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.Optional" %>
<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 13.06.2023
  Time: 18:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<%
    myDatabse dataBase = (myDatabse) request.getServletContext().getAttribute("dataBase");
    ArrayList<Product> list = dataBase.getProducts();

    for(int i = 0; i < list.size(); i++){
        Product product = list.get(i);
        String txt = "<li><a href=\"show-product.jsp?id=" + product.getProduct_id() + "\">"+ product.getProduct_name()+ "</a></li>";
        out.println(txt);
    }
%>

</body>
</html>
