package com.rant.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.rant.database.CouchDB;
import com.rant.database.CouchDBSetup;
import com.rant.objects.Config;
import com.rant.objects.Database;

/**
 * Unit tests for Application
 * 
 * @author amdelamar
 * @since 1.0.0
 */
@RunWith(JUnit4.class)
public class ApplicationTests {
    
    private Application app = new Application();
    
    @Test
    public void init() {
        //app.contextInitialized(null);
    }

    @Test
    public void settings() {
        
        Config config = app.loadSettingsFromFile(Application.getPropFile());
        Application.setConfig(config);
        assertNotNull(Application.getConfig());
        
        assertNotNull(Application.getString("name"));
        assertTrue(Application.getInt("limit") > 0);
        assertTrue(Application.getDouble("manageLimit") > 0d);
        
        Application.setString("test", "true");
        assertEquals("true", Application.getString("test"));
    }
    
    @Test
    public void database() {
        Database db = app.loadDatabase();
        assertNotNull(db);
        
        Application.setDatabaseSetup(new CouchDBSetup(db));
        assertNotNull(Application.getDatabaseSetup());
        
        Application.setDatabaseService(new CouchDB(db));
        assertNotNull(Application.getDatabaseService());
    }
    
    @Test
    public void destroy() {
        //app.contextDestroyed(null);
    }
}
