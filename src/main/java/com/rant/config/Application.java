package com.rant.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.rant.database.CouchDB;
import com.rant.database.DatabaseSource;
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

    @Override
    public void contextInitialized(ServletContextEvent servletContext) {

        // Load settings from properties file
        settingsMap = loadSettings(PROP_FILE);

        // Set Database
        database = loadDatabase();

        // Test Database
        if (!database.test()) {
            // failure
            System.exit(1);
        }

        System.out.println("Started Ranting!");
    }

    private HashMap<String, String> loadSettings(String propertiesFile) {
        HashMap<String, String> map = null;
        try {
            map = new HashMap<String, String>();
            Properties properties = new Properties();
            properties.load(Application.class.getResourceAsStream(propertiesFile));

            // put into map
            for (String key : properties.stringPropertyNames()) {
                String value = properties.getProperty(key);
                map.put(key, value);
            }

        } catch (IOException e) {
            System.err.println(e);
        }
        return map;
    }

    private DatabaseSource loadDatabase() {
        // check env variable
        String vcap = System.getenv("VCAP_SERVICES");
        Database db = new Database();
        if (vcap == null) {
            // if vcap is not available, then
            // run on local Database
            System.err.println(
                    "Failed to locate VCAP Object. Continuing with Datasource from properties.");

            db.setHost(getString("dbHost"));
            db.setPort(getString("dbPort"));
            db.setName(getString("dbName"));
            db.setUrl(getString("dbUrl"));
            db.setUsername(getString("dbUsername"));
            db.setPassword(getString("dbPassword"));
        } else {
            // try to read env variables
            try {
                JSONObject obj = new JSONObject(vcap);

                JSONArray cloudant = obj.getJSONArray("cloudantNoSQLDB");
                JSONObject couchdb = cloudant.getJSONObject(0).getJSONObject("credentials");
                db.setHost(couchdb.getString("host"));
                db.setPort("" + couchdb.getInt("port"));
                db.setName(couchdb.getString("name"));
                db.setUrl(couchdb.getString("url"));
                db.setUsername(couchdb.getString("username"));
                db.setPassword(couchdb.getString("password"));

            } catch (JSONException e) {
                System.err.println("Failed to parse JSON object VCAP_SERVICES for Datasource.");
                e.printStackTrace();
            }
        }
        return new CouchDB(db);
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