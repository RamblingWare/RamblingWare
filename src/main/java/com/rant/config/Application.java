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
import com.rant.database.CouchDBSetup;
import com.rant.database.DatabaseService;
import com.rant.database.DatabaseSetup;
import com.rant.objects.Config;
import com.rant.objects.Database;

/**
 * Application class loads the settings from application.properties file and and establishes the
 * DatabaseSource used for this app.
 * 
 * @author Austin Delamar
 * @date 4/8/2017
 */
public class Application implements ServletContextListener {

    private final static String PROP_FILE = "/rant.properties";
    private static Config config;
    private static DatabaseService databaseService;
    private static DatabaseSetup databaseSetup;

    @Override
    public void contextInitialized(ServletContextEvent servletContext) {

        // Load settings from File
        config = loadSettingsFromFile(PROP_FILE);

        // Set Database
        Database db = loadDatabase();
        System.out.println("Using Database:\r\n" + db.toString());
        databaseService = new CouchDB(db);

        // Test Database
        databaseSetup = new CouchDBSetup(db);
        if (!databaseSetup.test()) {
            // failure
            System.exit(1);
        } else if (!databaseSetup.verify()) {
            System.out.println("Setup detected the Database is not configured properly.");
            // perform first-time install
            if (!databaseSetup.install()) {
                // failed to install
                System.exit(1);
            } else {
                System.out.println("Setup Database completed.");
            }
        } else {
            System.out.println("Database was verified.");
        }

        // Load settings from Database
        Config configdb = loadSettingsFromDB(databaseService);
        config.getSettings().putAll(configdb.getSettings());

        System.out.println("Started App. Time to Relax.");
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

    private Config loadSettingsFromDB(DatabaseService dbs) {
        return dbs.getConfig();
    }

    private Database loadDatabase() {
        // check env variable
        String vcap = System.getenv("VCAP_SERVICES");
        Database db = new Database();
        if (vcap == null || vcap.isEmpty()) {
            // if env is not available, then
            // run on local couchdb
            System.err.println(
                    "Failed to locate VCAP Object. Continuing with Datasource from properties.");

            db.setHost(getString("couchdb.host"));
            db.setPort(getString("couchdb.port"));
            db.setName(getString("couchdb.name"));
            db.setUrl(getString("couchdb.url"));
            db.setUsername(getString("couchdb.username"));
            db.setPassword(getString("couchdb.password"));
        } else {
            // try to read env variables
            // for couchdb / cloudant
            try {
                Gson gson = new Gson();
                JsonObject obj = gson.fromJson(vcap, JsonObject.class);
                JsonArray cloudant = obj.getAsJsonArray("cloudantNoSQLDB");
                JsonObject couchdb = cloudant.get(0).getAsJsonObject()
                        .getAsJsonObject("credentials");
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
        return db;
    }

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        System.out.println("Stopped App.");
    }

    /**
     * Gets the currently used Config for this app.
     * 
     * @return Config
     */
    public static Config getConfig() {
        return config;
    }

    /**
     * Gets the currently used Database Service for this app.
     * 
     * @return DatabaseService
     */
    public static DatabaseService getDatabaseService() {
        return databaseService;
    }

    /**
     * Gets the currently used Database Setup for this app.
     * 
     * @return DatabaseSetup
     */
    public static DatabaseSetup getDatabaseSetup() {
        return databaseSetup;
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