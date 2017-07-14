package com.rant.config;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.dbcp.BasicDataSource;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
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

            db.setName(getString("db-name"));
            db.setHost(getString("db-host"));
            db.setPort(getString("db-port"));
            db.setUrl(getString("db-url"));
            db.setUsername(getString("db-user"));
            db.setPassword(getString("db-pass"));
        } else {
            // try to read env variables
            try {
                JSONObject obj = new JSONObject(vcap);
                JSONArray cleardb = obj.getJSONArray("cloudantNoSQLDB");
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
            bdbs.setDriverClassName(Application.getString("driver"));
            bdbs.setUrl("jdbc:mysql://" + db.getHost() + ":" + db.getPort() + "/" + db.getName());
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

        // CouchDB
        try {
            CloudantClient client = ClientBuilder.url(new URL(getString("dbUrl")))
                    .username(getString("dbUsername")).password(getString("dbPassword")).build();

            // Show the server version
            System.out.println("CouchDB Version: " + client.serverVersion());

            // Test Create, Insert, Delete
            com.cloudant.client.api.Database cloudant = client.database("rant-test", true);
            cloudant.save(db);
            client.deleteDB("rant-test");

        } catch (Exception e) {
            e.printStackTrace();
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
     * Get a Setting value as a String
     * 
     * @param key
     *            name of value
     * @return value String
     */
    public static String getString(String key) {
        return settingsMap.get(key);
    }

    /**
     * Get a Setting value as a String
     * 
     * @param key
     *            name of value
     * @return value int
     */
    public static int getInt(String key) throws NumberFormatException {
        return Integer.parseInt(settingsMap.get(key));
    }

    /**
     * Get a Setting value as a String
     * 
     * @param key
     *            name of value
     * @return value double
     */
    public static double getDouble(String key) throws NumberFormatException {
        return Double.parseDouble(settingsMap.get(key));
    }

    /**
     * Set a Setting value as a String
     * 
     * @param key
     *            name of value
     * @param value
     *            the value to set
     */
    public static void setString(String key, String value) {
        settingsMap.put(key, value);
    }

}