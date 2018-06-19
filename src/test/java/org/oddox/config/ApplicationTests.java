package org.oddox.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.oddox.OddoxVerticle;
import org.oddox.database.CouchDb;
import org.oddox.database.CouchDbSetup;
import org.oddox.database.Database;
import org.oddox.objects.Header;

/**
 * Unit tests for Application
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
    public void constructor() {
        assertTrue(app != null);
    }

    @Test
    public void settings() {
        AppConfig config = null;
        try {
            config = Application.loadSettingsFromFile(null);
            fail("null settings not caught");
        } catch (IOException e) {
            assertNull(config);
        }
        try {
            config = Application.loadSettingsFromFile(OddoxVerticle.APP_PROP_FILE);
            Application.setAppConfig(config);
        } catch (IOException e) {
            fail(e.getMessage());
        }
        assertNotNull(Application.getAppConfig());
        assertNotNull(Application.getString("name"));
        assertTrue(Application.getInt("resultsPerPage") > 0);
        assertTrue(Application.getDouble("rssFeedLimit") > 0d);

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
        assertEquals("value", config.getSettings()
                .get("key"));
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
        assertTrue(fw.getWhitelist()
                .contains("0.0.0.0"));

        List<String> blist = new ArrayList<String>();
        blist.add("8.8.8.8");
        fw.setBlacklist(blist);
        assertTrue(fw.getBlacklist()
                .contains("8.8.8.8"));

        Application.setAppFirewall(fw);
        assertNotNull(Application.getAppFirewall());
    }

    @Test
    public void headers() {
        AppHeaders hd = new AppHeaders();
        hd.set_Id("APPHEADERS");
        hd.set_Rev("1");
        assertEquals("APPHEADERS", hd.get_Id());
        assertEquals("1", hd.get_Rev());
        assertNotNull(hd.toString());

        List<Header> hlist = new ArrayList<Header>();
        hlist.add(new Header("Server", "Oddox"));
        hd.setHeaders(hlist);
        assertEquals(1, hd.getHeaders()
                .size());
        assertEquals("Server", hd.getHeaders()
                .get(0)
                .getKey());
        assertEquals("Oddox", hd.getHeaders()
                .get(0)
                .getValue());

        Application.setAppHeaders(hd);
        assertNotNull(Application.getAppHeaders());
    }

    @Test
    public void databaseSetup() {
        Application.setDatabaseSetup(new CouchDbSetup(new Database()));
        assertNotNull(Application.getDatabaseSetup());

        Application.setDatabaseService(new CouchDb(new Database()));
        assertNotNull(Application.getDatabaseService());

    }

    @Test
    public void databaseLoadCf() {
        Database db = null;

        // Properties file
        try {
            db = Application.loadDatabase(null, OddoxVerticle.DB_PROP_FILE);
            assertNotNull(db);
        } catch (IOException e) {
            fail(e.getMessage());
        }

        // CloudFoundry env variables
        HashMap<String, String> cfenv = new HashMap<String, String>();
        // @formatter:off
        cfenv.put("VCAP_SERVICES", "{\r\n" + 
                "    \"cloudantNoSQLDB\": [\r\n" + 
                "        {\r\n" + 
                "            \"credentials\": {\r\n" + 
                "                \"username\": \"myaccount\",\r\n" + 
                "                \"password\": \"mypassword\",\r\n" + 
                "                \"host\": \"myaccount.cloudant.com\",\r\n" + 
                "                \"port\": 443,\r\n" + 
                "                \"url\": \"https://myaccount:mypassword@myaccount.cloudant.com\"\r\n" + 
                "            },\r\n" + 
                "            \"syslog_drain_url\": null,\r\n" + 
                "            \"volume_mounts\": [],\r\n" + 
                "            \"label\": \"cloudantNoSQLDB\",\r\n" + 
                "            \"provider\": null,\r\n" + 
                "            \"plan\": \"Lite\",\r\n" + 
                "            \"name\": \"Cloudant\",\r\n" + 
                "            \"tags\": [\r\n" + 
                "                \"data_management\",\r\n" + 
                "                \"ibm_created\",\r\n" + 
                "                \"lite\",\r\n" + 
                "                \"ibm_dedicated_public\"\r\n" + 
                "            ]\r\n" + 
                "        }\r\n" + 
                "    ]\r\n" + 
                "}");
        // @formatter:on
        try {
            db = Application.loadDatabase(cfenv, null);
            assertNotNull(db);
        } catch (IOException e) {
            fail(e.getMessage());
        }

        // invalid VCAP_SERVICES
        cfenv.put("VCAP_SERVICES", "");
        try {
            db = Application.loadDatabase(cfenv, null);
            fail("failed to catch invalid VCAP_SERVICES env");
        } catch (IOException e) {
            // ok good
        }
    }
    
    @Test
    public void databaseLoadFile() {
        Database db = null;
        
        // db.properties file
        try {
            db = Application.loadDatabase(null, "/db.properties");
            assertNotNull(db);
        } catch (IOException e) {
            fail(e.getMessage());
        }

        // invalid properties file
        try {
            db = Application.loadDatabase(null, "file-doesnt-exist.properties");
            fail("failed to catch null properties file");
        } catch (IOException e) {
            // ok good
        }
    }
    
    @Test
    public void databaseLoadEnv() {
        Database db = null;
        
        // Docker env variables
        HashMap<String, String> dockerenv = new HashMap<String, String>();
        dockerenv.put("DB_URL", "http://127.0.0.1:5984/");
        dockerenv.put("DB_USER", "admin");
        dockerenv.put("DB_PASS", "admin");
        try {
            db = Application.loadDatabase(dockerenv, null);
            assertNotNull(db);
        } catch (IOException e) {
            fail(e.getMessage());
        }

        // invalid DB_URL
        dockerenv.put("DB_URL", "");
        try {
            db = Application.loadDatabase(dockerenv, null);
            fail("failed to catch invalid DB_URL env");
        } catch (IOException e) {
            // ok good
        }
    }
}
