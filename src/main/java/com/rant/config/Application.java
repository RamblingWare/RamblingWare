package com.rant.config;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.dbcp.BasicDataSource;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.rant.database.DatabaseSource;
import com.rant.database.MySQLDatabase;
import com.rant.model.Database;

/**
 * Application class loads the settings from application.properties file and and establishes the
 * DatabaseSource used for this app.
 * 
 * @author Austin Delamar
 * @date 4/8/2017
 */
public class Application implements ServletContextListener {

    private final static String PROP_FILE = "/application.properties";

    private static HashMap<String, String> settingsMap;
    private static DatabaseSource database;
    private static BasicDataSource bdbs;
    private static int limit;
    private static int manageLimit;

    @Override
    public void contextInitialized(ServletContextEvent servletContext) {

        try {
            // load settings from properties file
            settingsMap = new HashMap<String, String>();
            Properties properties = new Properties();
            properties.load(Application.class.getResourceAsStream(PROP_FILE));

            // put into map
            for (String key : properties.stringPropertyNames()) {
                String value = properties.getProperty(key);
                settingsMap.put(key, value);
            }

        } catch (IOException e) {
            System.err.println(e);
        }

        // check env variable
        String vcap = System.getenv("VCAP_SERVICES");
        Database db = new Database();
        if (vcap == null) {
            // if vcap is not available, then
            // run on local MySQL Database
            System.err.println(
                    "Failed to locate VCAP Object. Continuing with Datasource from properties.");

            db.setName(getSetting("db-name"));
            db.setHost(getSetting("db-host"));
            db.setPort(getSetting("db-port"));
            db.setUrl(getSetting("db-url"));
            db.setUsername(getSetting("db-user"));
            db.setPassword(getSetting("db-pass"));
        } else {
            // try to read env variables
            try {
                JSONObject obj = new JSONObject(vcap);
                JSONArray cleardb = obj.getJSONArray("cleardb");
                JSONObject mysql = cleardb.getJSONObject(0).getJSONObject("credentials");
                db.setName(mysql.getString("name"));
                db.setHost(mysql.getString("hostname"));
                db.setPort("" + mysql.getInt("port"));
                db.setUrl(mysql.getString("uri"));
                db.setUsername(mysql.getString("username"));
                db.setPassword(mysql.getString("password"));

            } catch (JSONException e) {
                System.err.println("Failed to parse JSON object VCAP_SERVICES for Datasource.");
                e.printStackTrace();
            }
        }

        try {
            // Construct BasicDataSource
            bdbs = new BasicDataSource();
            bdbs.setDriverClassName(Application.getSetting("driver"));
            bdbs.setUrl(
                    "jdbc:mysql://" + db.getHost() + ":" + db.getPort() + "/" + db.getName());
            bdbs.setUsername(db.getUsername());
            bdbs.setPassword(db.getPassword());
            bdbs.setMaxActive(4);
            bdbs.setMaxIdle(4);
            bdbs.setMinIdle(1);
            bdbs.setMaxWait(10000);
            bdbs.setTimeBetweenEvictionRunsMillis(5000);
            bdbs.setMinEvictableIdleTimeMillis(60000);    
            bdbs.setValidationQuery("SELECT 1");
            bdbs.setValidationQueryTimeout(3);
            bdbs.setTestOnBorrow(true);
            bdbs.setTestWhileIdle(true);
            bdbs.setTestOnReturn(false);
                    
            database = new MySQLDatabase(db);

        } catch (Exception e) {
            System.err.println("Failed to bind JNDI for BasicDatabaseSource.");
            e.printStackTrace();
        }

        try {
            // set result limit per page
            setLimit(Integer.parseInt(getSetting("limit")));
        } catch (Exception e) {
            // default 10
            setLimit(10);
        }
        try {
            // set management limit per page
            setManageLimit(Integer.parseInt(getSetting("manageLimit")));
        } catch (Exception e) {
            // default 15
            setManageLimit(15);
        }

        System.out.println("Started Ranting!");
    }

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        System.out.println("Stopped Ranting.");
    }

    /**
     * Gets the currently used Database Service for this app.
     * 
     * @return Database
     */
    public static DatabaseSource getDatabaseSource() {
        return database;
    }

    /**
     * Gets the JNDI bound BasicDatabaseSource for this app.
     * 
     * @return BasicDatabaseSource
     */
    public static BasicDataSource getBasicDatabaseSource() {
        return bdbs;
    }

    /**
     * Get a Setting value
     * 
     * @param key
     *            name of value
     * @return value
     */
    public static String getSetting(String key) {
        return settingsMap.get(key);
    }

    /**
     * Set a Setting value
     * 
     * @param key
     *            name of value
     * @param value
     *            the value to set
     */
    public static void setSetting(String key, String value) {
        settingsMap.put(key, value);
    }

    /**
     * Get the global page result limit.
     * 
     * @return limit
     */
    public static int getLimit() {
        return limit;
    }

    /**
     * Set the global page result limit.
     * 
     * @param limit
     *            integer between 3 and 25. (10 default)
     */
    public static void setLimit(int limit) {

        if (limit < 3) {
            limit = 3;
        } else if (limit > 25) {
            limit = 25;
        }

        Application.limit = limit;
    }
    
    /**
     * Get the global management result limit.
     * 
     * @return manageLimit
     */
    public static int getManageLimit() {
        return manageLimit;
    }

    /**
     * Set the global management result limit.
     * 
     * @param manageLimit
     *            integer between 3 and 25. (15 default)
     */
    public static void setManageLimit(int manageLimit) {

        if (limit < 3) {
            limit = 3;
        } else if (limit > 25) {
            limit = 25;
        }

        Application.manageLimit = manageLimit;
    }
}