package com.rant.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
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
    private static Config config;
    private static DatabaseSource database;

    @Override
    public void contextInitialized(ServletContextEvent servletContext) {

        // Load settings from properties file
        config = loadSettingsFromFile(PROP_FILE);

        // Set Database
        database = loadDatabase();

        // Test Database
        if (!database.test()) {
            // failure
            System.exit(1);
        }
        
        // Load settings from Database
        Config configdb = loadSettingsFromDB(database);
        config.getSettings().putAll(configdb.getSettings());
        
        for(String s : config.getSettings().values()) {
            System.out.println(s);
        }

        System.out.println("Started Ranting!");
    }

    private Config loadSettingsFromFile(String propertiesFile) {
        Config config = new Config();
        try {
            HashMap<String, String> map = new HashMap<String, String>();
            Properties properties = new Properties();
            properties.load(Application.class.getResourceAsStream(propertiesFile));

            // put into map
            for (String key : properties.stringPropertyNames()) {
                String value = properties.getProperty(key);
                map.put(key, value);
            }
            
            config.setSettings(map);

        } catch (IOException e) {
            System.err.println(e);
        }
        return config;
    }
    
    private Config loadSettingsFromDB(DatabaseSource dbs) {
        return dbs.getConfig();
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
                Gson gson = new Gson();
                JsonObject obj = gson.fromJson(vcap, JsonObject.class);
                JsonArray cloudant = obj.getAsJsonArray("cloudantNoSQLDB");
                JsonObject couchdb = cloudant.get(0).getAsJsonObject().getAsJsonObject("credentials");
                db.setHost(couchdb.get("host").getAsString());
                db.setPort(couchdb.get("port").getAsString());
                db.setName(couchdb.get("name").getAsString());
                db.setUrl(couchdb.get("url").getAsString());
                db.setUsername(couchdb.get("username").getAsString());
                db.setPassword(couchdb.get("password").getAsString());

            } catch (Exception e) {
                System.err.println("Failed to parse VCAP_SERVICES for Datasource properties.");
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
        return config.getSettings().get(key);
    }

    /**
     * Get a Setting value as a String
     * 
     * @param key
     *            name of value
     * @return value int
     */
    public static int getInt(String key) throws NumberFormatException {
        return Integer.parseInt(config.getSettings().get(key));
    }

    /**
     * Get a Setting value as a String
     * 
     * @param key
     *            name of value
     * @return value double
     */
    public static double getDouble(String key) throws NumberFormatException {
        return Double.parseDouble(config.getSettings().get(key));
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
        config.getSettings().put(key, value);
    }

}