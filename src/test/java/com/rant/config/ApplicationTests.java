package com.rant.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import com.rant.database.CouchDb;
import com.rant.database.CouchDbSetup;
import com.rant.database.Database;

/**
 * Unit tests for Application
 * 
 * @author amdelamar
 * @since 1.0.0
 */
@RunWith(JUnit4.class)
public class ApplicationTests {

    @InjectMocks
    private Application app;

    @InjectMocks
    private CouchDb dbs;

    @Before
    public void beforeEachTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void settings() {
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
    public void config() {
        AppConfig config = new AppConfig();
        config.set_Id("APPCONFIG");
        config.set_Rev("1");
        assertEquals("APPCONFIG", config.get_Id());
        assertEquals("1", config.get_Rev());
        assertNotNull(config.toString());

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("key", "value");
        config.setSettings(map);
        assertNotNull(config.getSettings());
        assertEquals("value", config.getSettings().get("key"));
    }

    @Test
    public void firewall() {
        AppFirewall fw = new AppFirewall();
        fw.set_Id("APPFIREWALL");
        fw.set_Rev("1");
        fw.setEnabled(true);
        assertEquals("APPFIREWALL", fw.get_Id());
        assertEquals("1", fw.get_Rev());
        assertTrue(fw.isEnabled());
        assertNotNull(fw.toString());

        List<String> wlist = new ArrayList<String>();
        wlist.add("0.0.0.0");
        fw.setWhitelist(wlist);
        assertTrue(fw.getWhitelist().contains("0.0.0.0"));

        List<String> blist = new ArrayList<String>();
        blist.add("8.8.8.8");
        fw.setBlacklist(blist);
        assertTrue(fw.getBlacklist().contains("8.8.8.8"));

        Application.setAppFirewall(fw);
        assertNotNull(Application.getAppFirewall());
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
}
