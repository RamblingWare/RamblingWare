package com.rant.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;

import com.rant.database.CouchDB;
import com.rant.database.CouchDBSetup;
import com.rant.database.DatabaseService;
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

    private Application app;

    @InjectMocks
    private DatabaseService dbs;

    @Before
    public void beforeEachTest() {
        app = new Application();
    }

    @Test
    public void settingsFromFile() {

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
    public void properties() {
        assertNotNull(Application.getPropFile());
        assertTrue(Application.getPropFile().endsWith(".properties"));
    }

    @Test
    public void init() {
        // app.contextInitialized(null);
    }

    @Test
    public void destroy() {
        app.contextDestroyed(null);
    }
}
