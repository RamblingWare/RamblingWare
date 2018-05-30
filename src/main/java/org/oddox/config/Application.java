package org.oddox.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.oddox.database.Database;
import org.oddox.database.DatabaseService;
import org.oddox.database.DatabaseSetup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * Application class loads settings from the properties files and and establishes the
 * DatabaseSource used for this app. Also, acts as the main starting point for the app.
 * 
 * @author amdelamar
 * @date 4/8/2017
 */
public final class Application {

    private static Logger logger = LoggerFactory.getLogger(Application.class);
    private static AppConfig appConfig;
    private static AppFirewall appFirewall;
    private static AppHeaders appHeaders;
    private static DatabaseService databaseService;
    private static DatabaseSetup databaseSetup;
    
    private Application() {
        // prevent instantiation
    }

    /**
     * Loads the Application configuration from the properties file.
     * @param propFile APP_PROP_FILE
     * @return AppConfig
     * @throws IOException if propFile is invalid or not found
     */
    public static AppConfig loadSettingsFromFile(String propFile) throws IOException {
        AppConfig config = null;
        try {
            HashMap<String, String> map = Utils.loadMapFromFile(propFile);
            config = new AppConfig();
            config.setSettings(map);
        } catch (Exception e) {
            throw new IOException(e);
        }
        return config;
    }

    /**
     * Loads the Database URL, Host, Port, User, and Password, from either the System environment variables or the properties file.
     * @param env System.getenv()
     * @param propFile DB_PROP_FILE
     * @return Database
     * @throws IOException if failed to load either parameters
     */
    public static Database loadDatabase(Map<String, String> env, String propFile) throws IOException {
        Database db = null;

        // check VCAP env variable
        if (env != null && env.containsKey("VCAP_SERVICES")) {
            try {
                // try to read cloudfoundry env variables
                db = loadDatabaseFromCloudFoundryEnv(env);

            } catch (Exception e) {
                logger.error("ERROR: Failed to parse VCAP_SERVICES for Datasource properties.");
                throw new IOException(e);
            }
        }

        // check DB_URL env variable
        else if (env != null && env.containsKey("DB_URL")) {
            try {
                // try read docker env variables
                db = loadDatabaseFromDockerEnv(env);

            } catch (Exception e) {
                logger.error("ERROR: Failed to parse DB_URL for Datasource properties.");
                throw new IOException(e);
            }
        }

        // otherwise fallback to db.properties
        else {
            try {
                logger.warn("WARNING: No DB environment variables provided. Continuing with DB from " + propFile
                        + " file.");

                // try to read from db.properties file
                db = loadDatabaseFromProperties(propFile);

            } catch (Exception e) {
                logger.error("ERROR: Failed to parse " + propFile + " file.");
                throw new IOException(e);
            }
        }

        return db;
    }

    private static Database loadDatabaseFromCloudFoundryEnv(Map<String, String> env) throws IOException {
        Database db = new Database();
        try {
            // try to read cloud foundry env variables
            String vcap = env.get("VCAP_SERVICES");
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
            throw new IOException(e);
        }
        return db;
    }

    private static Database loadDatabaseFromDockerEnv(Map<String, String> env) throws IOException {
        Database db = new Database();
        try {
            // try read docker env variables
            String dbUrl = env.get("DB_URL");
            db.setUrl(dbUrl);
            String host = dbUrl.replace("http://", "");
            host = host.replace("https://", "");
            db.setPort(host.substring(host.indexOf(":") + 1, host.length()));
            host = host.substring(0, host.indexOf(":"));
            db.setHost(host);
            db.setUsername(env.get("DB_USER"));
            db.setPassword(env.get("DB_PASS"));
        } catch (Exception e) {
            throw new IOException(e);
        }
        return db;
    }

    private static Database loadDatabaseFromProperties(String propFile) throws IOException {
        Database db = new Database();
        try {
            // try to read properties file
            HashMap<String, String> map = Utils.loadMapFromFile(propFile);
            db.setHost(map.get("couchdb.host"));
            db.setPort(map.get("couchdb.port"));
            db.setUrl(map.get("couchdb.url"));
            db.setUsername(map.get("couchdb.username"));
            db.setPassword(map.get("couchdb.password"));
        } catch (Exception e) {
            throw new IOException(e);
        }
        return db;
    }

    public static AppConfig getAppConfig() {
        return appConfig;
    }

    public static void setAppConfig(AppConfig appConfig) {
        Application.appConfig = appConfig;
    }

    public static AppFirewall getAppFirewall() {
        return appFirewall;
    }

    public static void setAppFirewall(AppFirewall appFirewall) {
        Application.appFirewall = appFirewall;
    }

    public static AppHeaders getAppHeaders() {
        return appHeaders;
    }

    public static void setAppHeaders(AppHeaders appHeaders) {
        Application.appHeaders = appHeaders;
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
