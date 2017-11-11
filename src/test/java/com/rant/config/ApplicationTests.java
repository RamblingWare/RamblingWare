package com.rant.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;

import com.rant.database.CouchDb;
import com.rant.database.CouchDbSetup;
import com.rant.database.DatabaseService;
import com.rant.objects.AppConfig;
import com.rant.objects.AppFirewall;
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
        AppConfig config = Application.loadSettingsFromFile(null);
        assertNull(config);
        config = Application.loadSettingsFromFile(Application.getPropFile());
        Application.setAppConfig(config);
        assertNotNull(Application.getAppConfig());

        assertNotNull(Application.getString("name"));
        assertTrue(Application.getInt("default.limit") > 0);
        assertTrue(Application.getDouble("default.manageLimit") > 0d);

        Application.setString("test", "true");
        assertEquals("true", Application.getString("test"));
    }
    
    @Test
    public void firewall() {
        AppFirewall fw = new AppFirewall();
        fw.setEnabled(true);
        Application.setAppFirewall(fw);
        assertNotNull(Application.getAppFirewall());
        assertTrue(Application.getAppFirewall().isEnabled());
    }

    @Test
    public void database() {
        Database db = Application.loadDatabase();
        assertNotNull(db);

        Application.setDatabaseSetup(new CouchDbSetup(db));
        assertNotNull(Application.getDatabaseSetup());

        Application.setDatabaseService(new CouchDb(db));
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
