package org.oddox.config;

import java.util.HashMap;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.oddox.database.CouchDb;
import org.oddox.database.CouchDbSetup;
import org.oddox.database.Database;
import org.oddox.database.DatabaseService;
import org.oddox.database.DatabaseSetup;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * Application class loads settings from the properties files and and establishes the
 * DatabaseSource used for this app. Also, acts as the main starting point for the app.
 * 
 * @author Austin Delamar
 * @date 4/8/2017
 */
public class Application implements ServletContextListener {

    public final static String APP_PROP_FILE = "/app.properties";
    public final static String DB_PROP_FILE = "/db.properties";

    private static AppConfig appConfig;
    private static AppFirewall appFirewall;
    private static DatabaseService databaseService;
    private static DatabaseSetup databaseSetup;

    @Override
    public void contextInitialized(ServletContextEvent servletContext) {

        System.out.println("Starting up Oddox...");

        // Load settings from File
        appConfig = loadSettingsFromFile(APP_PROP_FILE);
        appFirewall = new AppFirewall();

        // Setup Database
        try {
            Database db = loadDatabase();
            System.out.println("Using Database:\r\n" + db.toString());
            databaseSetup = new CouchDbSetup(db);
            if (!databaseSetup.setup()) {
                System.exit(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        // Load settings from Database
        databaseService = new CouchDb(databaseSetup.getDatabase());
        appFirewall = loadFirewallFromDB(databaseService);
        AppConfig configdb = loadSettingsFromDB(databaseService);
        if (configdb != null) {
            System.out.println("Found app settings in the database. Using that instead of " + APP_PROP_FILE);
            appConfig.getSettings()
                    .putAll(configdb.getSettings());
        }

        // Ready
        System.out.println("Oddox is ready.");
    }

    public static AppConfig loadSettingsFromFile(String propertiesFile) {
        AppConfig config = new AppConfig();
        try {
            HashMap<String, String> map = Utils.loadMapFromFile(propertiesFile);
            config.setSettings(map);

        } catch (Exception e) {
            System.out.println("WARNING: Properties file not found or failed to load properly.");
            return null;
        }
        return config;
    }

    public static AppConfig loadSettingsFromDB(DatabaseService dbs) {
        return dbs.getAppConfig();
    }

    public static AppFirewall loadFirewallFromDB(DatabaseService dbs) {
        return dbs.getAppFirewall();
    }

    public static Database loadDatabase() {
        // check env variable
        String dbUrl = System.getenv("DB_URL");
        String vcap = System.getenv("VCAP_SERVICES");
        Database db = new Database();
        if (vcap != null && !vcap.isEmpty()) {
            // try to read env variables
            // for couchdb / cloudant
            try {
                Gson gson = new Gson();
                JsonObject obj = gson.fromJson(vcap, JsonObject.class);
                JsonArray cloudant = obj.getAsJsonArray("cloudantNoSQLDB");
                JsonObject couchdb = cloudant.get(0)
                        .getAsJsonObject()
                        .getAsJsonObject("credentials");
                db.setHost(couchdb.get("host")
                        .getAsString());
                db.setPort(couchdb.get("port")
                        .getAsString());
                db.setUrl(couchdb.get("url")
                        .getAsString());
                db.setUsername(couchdb.get("username")
                        .getAsString());
                db.setPassword(couchdb.get("password")
                        .getAsString());

            } catch (Exception e) {
                System.out.println("ERROR: Failed to parse VCAP_SERVICES for Datasource properties.");
                e.printStackTrace();
            }
        } else if (dbUrl != null && !dbUrl.isEmpty()) {
            try {
                // try read docker env variables
                db.setUrl(dbUrl);
                String host = dbUrl.replace("http://", "");
                host = host.replace("https://", "");
                db.setPort(host.substring(host.indexOf(":") + 1, host.length()));
                host = host.substring(0, host.indexOf(":"));
                db.setHost(host);
                db.setUsername(System.getenv("DB_USER"));
                db.setPassword(System.getenv("DB_PASS"));
            } catch (Exception e) {
                System.out.println("ERROR: Failed to parse DB_URL for Datasource properties.");
                e.printStackTrace();
            }
        } else {
            // if env is not available, then
            // run on local couchdb in db.properties
            System.out.println("WARNING: No DB environment variables provided. Continuing with DB from " + DB_PROP_FILE
                    + " file.");

            try {
                HashMap<String, String> map = Utils.loadMapFromFile(DB_PROP_FILE);
                db.setHost(map.get("couchdb.host"));
                db.setPort(map.get("couchdb.port"));
                db.setUrl(map.get("couchdb.url"));
                db.setUsername(map.get("couchdb.username"));
                db.setPassword(map.get("couchdb.password"));
            } catch (Exception e) {
                System.out.println("ERROR: Failed to parse " + DB_PROP_FILE + " file.");
                e.printStackTrace();
                System.exit(2);
            }
        }

        return db;
    }

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        System.out.println("Stopped Oddox.");
    }

    /**
     * Gets the currently used AppConfig for this app.
     * 
     * @return Config
     */
    public static AppConfig getAppConfig() {
        return appConfig;
    }

    /**
     * Sets the configuration settings for this app.
     * 
     * @param config
     */
    public static void setAppConfig(AppConfig appConfig) {
        Application.appConfig = appConfig;
    }

    public static AppFirewall getAppFirewall() {
        return appFirewall;
    }

    public static void setAppFirewall(AppFirewall appFirewall) {
        Application.appFirewall = appFirewall;
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
     * Sets the Database Service for this app.
     * 
     * @param databaseService
     */
    public static void setDatabaseService(DatabaseService databaseService) {
        Application.databaseService = databaseService;
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
     * Sets the Database Setup for this app.
     * 
     * @param databaseSetup
     */
    public static void setDatabaseSetup(DatabaseSetup databaseSetup) {
        Application.databaseSetup = databaseSetup;
    }

    /**
     * Get a Setting value as a String
     * 
     * @param key
     *            name of value
     * @return value String
     */
    public static String getString(String key) {
        return appConfig.getSettings()
                .get(key);
    }

    /**
     * Get a Setting value as a String
     * 
     * @param key
     *            name of value
     * @return value int
     */
    public static int getInt(String key) throws NumberFormatException {
        return Integer.parseInt(appConfig.getSettings()
                .get(key));
    }

    /**
     * Get a Setting value as a String
     * 
     * @param key
     *            name of value
     * @return value double
     */
    public static double getDouble(String key) throws NumberFormatException {
        return Double.parseDouble(appConfig.getSettings()
                .get(key));
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
        appConfig.getSettings()
                .put(key, value);
    }

}
