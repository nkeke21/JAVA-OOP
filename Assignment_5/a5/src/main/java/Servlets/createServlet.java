package Servlets;
import Dao.accountManager;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "create", urlPatterns = "/create")
public class createServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        accountManager accountManager = (accountManager)getServletContext().getAttribute(Listener.atrManager);

        String name = request.getParameter("username");
        String password = request.getParameter("password");
        String view = "";
        if(accountManager.contains(name)){
            view = "alreadyInUse";
        } else {
            view = "Welcome";
            accountManager.add(name, password);
        }
        RequestDispatcher rd = request.getRequestDispatcher(view + ".jsp");
        rd.forward(request, response);
    }
}
