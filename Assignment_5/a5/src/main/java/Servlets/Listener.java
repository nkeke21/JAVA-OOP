package Servlets;

import Dao.accountManager;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class Listener implements ServletContextListener {
    public static final String atrManager = "manager Attribute";
    private accountManager manager;
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        manager = accountManager.getInstance();
        servletContextEvent.getServletContext().setAttribute(atrManager, manager);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
