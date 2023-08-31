<%@ page import="store.myDatabse" %>
<%@ page import="store.Product" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: user
  Date: 13.06.2023
  Time: 20:30
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

    for(int i = 0; i < list.size(); i ++){

        Product tmp = list.get(i);

        if(tmp.getProduct_id().equals(request.getParameter("id"))){
            out.println("<form action=\"servletFirst\" method=\"post\">");
            out.println("<h1>" + tmp.getProduct_name() + "</h1>");
            out.println("<img src=" +"/store-images/" + tmp.getProduct_image() + ">");
            out.println("<p> $" + tmp.getProduct_price() + "</p>");
            out.println("<input type=\"submit\" value=\"add to cart\">");
            out.println("<input name=\"id\" type=\"hidden\" value=" + tmp.getProduct_id() + ">");
            out.println("</form>");
        }
    }
%>

</body>
</html>
