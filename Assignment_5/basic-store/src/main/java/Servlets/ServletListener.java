package Servlets;

import store.Product;
import store.myDatabse;
import store.shoppingCart;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class ServletListener implements ServletContextListener, HttpSessionListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        myDatabse db = new myDatabse();
        servletContextEvent.getServletContext().setAttribute("dataBase", db);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }


    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        httpSessionEvent.getSession().setAttribute("shoppingCart", new shoppingCart());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {

    }
}
