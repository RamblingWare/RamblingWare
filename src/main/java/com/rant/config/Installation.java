package com.rant.config;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Installation class to setup the database schema and pre-populate initial values.
 * 
 * @author Austin Delamar
 * @date 7/06/2017
 */
public class Installation implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        System.out.println("Started installation of Rant.");

        // TODO create db if not exists

        // TODO create tables if not exists

        // TODO populate config with app.properties

        // TODO populate roles
        // Author, Editor, Owner, Admin

        // TODO populate users if empty
        // place 1 admin user with default password, no 2FA
    }

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        System.out.println("Installation of Rant finished.");
    }
}
