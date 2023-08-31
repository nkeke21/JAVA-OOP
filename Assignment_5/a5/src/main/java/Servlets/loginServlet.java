package Servlets;
import Dao.accountManager;

import javax.script.ScriptContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "login", urlPatterns = "/login")
public class loginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        accountManager accountManager = (accountManager)getServletContext().getAttribute(Listener.atrManager);

        String name = request.getParameter("username");
        String password = request.getParameter("password");
        String view = "";
        if(accountManager.contains(name) && accountManager.checkPassword(name, password)){
            view = "Welcome";
        } else {
            view = "Incorrect";
        }
        RequestDispatcher rd = request.getRequestDispatcher(view + ".jsp");
        rd.forward(request, response);
    }

}
