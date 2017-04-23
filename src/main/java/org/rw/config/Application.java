package org.rw.config;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.rw.action.model.Database;
import org.rw.database.DatabaseSource;
import org.rw.database.MySQLDatabase;

/**
 * Application class loads the settings from application.properties file
 * and and establishes the DatabaseSource used for this app.
 * 
 * @author Austin Delamar
 * @date 4/8/2017
 */
public class Application implements ServletContextListener {

	private final static String PROP_FILE = "/application.properties";
	
	private static HashMap<String, String> settingsMap;
	private static DatabaseSource database;

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		
		try{
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
		String dbHost, dbName, dbPort;
		String dbUrl, dbUser, dbPass;
		if(vcap == null) {
			// if vcap is not available, then
			// run on local MySQL Database
			System.err.println("Failed to locate VCAP Object. Continuing with Datasource from properties.");
			
			dbName = getSetting("db-name");
			dbHost = getSetting("db-host");
			dbPort = getSetting("db-port");
			dbUrl = getSetting("db-url");
			dbUser = getSetting("db-user");
			dbPass = getSetting("db-pass");
		}
		else {
			// try to read env variables
			try {
				JSONObject obj = new JSONObject(vcap);
				JSONArray cleardb = obj.getJSONArray("cleardb");
				JSONObject mysql = cleardb.getJSONObject(0).getJSONObject("credentials");
				dbName = mysql.getString("name");
				dbHost = mysql.getString("hostname");
				dbPort = ""+mysql.getInt("port");
				dbUrl = mysql.getString("uri");
				dbUser = mysql.getString("username");
				dbPass = mysql.getString("password");
				
				// VCAP_SERVICES were read successfully.
				
			} catch (JSONException e) {
				System.err.println("Failed to parse JSON object. Using local Db...");
				e.printStackTrace();
				
				dbName = getSetting("db-name");
				dbHost = getSetting("db-host");
				dbPort = getSetting("db-port");
				dbUrl = getSetting("db-url");
				dbUser = getSetting("db-user");
				dbPass = getSetting("db-pass");
			}
		}
		
		Database db = new Database(dbName);
		db.setHost(dbHost);
		db.setPort(dbPort);
		db.setUrl(dbUrl);
		db.setUsername(dbUser);
		db.setPassword(dbPass);
		
		database = new MySQLDatabase(db);
		
		System.out.println("Ready to start blogging!");
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {		
		;
	}
	
	/**
	 * Gets the currently used Database Service for this app.
	 * @return
	 */
	public static DatabaseSource getDatabaseSource(){
		return database;
	}

	public static String getSetting(String key) {
		return settingsMap.get(key);
	}

	public static void setSetting(String key, String value) {
		settingsMap.put(key, value);
	}
}