package ua.nure.tur.web.listeners;

import ua.nure.tur.db.dao.DAOManagerFactory;
import ua.nure.tur.db.dao.mysql.MysqlDAOManagerFactory;
import ua.nure.tur.exceptions.DataAccessException;
import ua.nure.tur.services.ServiceFactory;
import ua.nure.tur.services.impl.ServiceFactoryImpl;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

public class ContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        Properties properties = new Properties();
        try {
            properties.load(servletContextEvent.getServletContext().getResourceAsStream("/WEB-INF/db_connection.properties"));
            String daoFactoryClassName = properties.getProperty("dao_manager_factory.class");
            String resourceName = properties.getProperty("context.resource.db");
            Class<?> daoFactoryClass = Class.forName(daoFactoryClassName);
            DAOManagerFactory daoManagerFactory = (DAOManagerFactory) daoFactoryClass.getDeclaredConstructor(String.class).newInstance(resourceName);
            ServiceFactory.setFactory(new ServiceFactoryImpl(daoManagerFactory));
        } catch ( IOException | ClassNotFoundException | IllegalAccessException |
                InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            //TODO: log exceptions
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }
}
