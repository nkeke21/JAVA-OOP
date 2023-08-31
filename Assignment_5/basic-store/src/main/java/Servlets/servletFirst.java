package Servlets;

import org.springframework.cglib.core.EmitUtils;
import store.Product;
import store.myDatabse;
import store.shoppingCart;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;

@WebServlet(name="servletFirst", urlPatterns = "/servletFirst")
public class servletFirst extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        shoppingCart sc = (shoppingCart) request.getSession().getAttribute("shoppingCart");
        myDatabse db = (myDatabse) request.getServletContext().getAttribute("dataBase");
        ArrayList<Product> products = db.getProducts();

        String id = request.getParameter("id");

        if(id == null){

            Enumeration<String> enumr = request.getParameterNames();

            while(enumr.hasMoreElements()){

                String id2 = enumr.nextElement();

                for(int i = 0; i < products.size(); i++){
                    if(products.get(i).getProduct_id().equals(id2)){
                        int val = Integer.parseInt(request.getParameter(id2));
                        sc.addProduct(products.get(i), val);
                        break;
                    }
                }
            }

        }else{

            for(int i = 0; i < products.size(); i++){
                if(products.get(i).getProduct_id().equals(id)){
                    if(sc.contains(products.get(i))){
                        sc.addProduct(products.get(i), sc.getValue(products.get(i)) + 1);
                    }else{
                        sc.addProduct(products.get(i), 1);
                    }
                    break;
                }
            }

        }



        RequestDispatcher rd = request.getRequestDispatcher("shopping-cart.jsp");
        rd.forward(request, response);
    }
}
