<%@ page import="store.shoppingCart" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="store.Product" %><%--
  Created by IntelliJ IDEA.
  User: user
  Date: 13.06.2023
  Time: 20:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<form action="servletFirst" method="post">
<ul>
    <%
        shoppingCart sc = (shoppingCart) session.getAttribute("shoppingCart");
        ArrayList<Product> products = sc.getProducts();
        double sum = 0;
        for(int i = 0; i < products.size(); i++) {

            sum += products.get(i).getProduct_price() * sc.getValue(products.get(i));
            out.println("<li>");
            out.println("<input type ='number' value=" + sc.getValue(products.get(i)) +" name=" + products.get(i).getProduct_id() + ">" +
               products.get(i).getProduct_name() +  ", " + products.get(i).getProduct_price());
            out.println("</li>");
        }



    %>
</ul>
    Total: $ <%= sum %> <input type="submit" value="Update Cart"/>
</form>
    <a href="index.jsp">Continue shopping</a>


</body>
</html>
