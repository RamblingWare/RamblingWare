package org.oddox.action.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.oddox.OddoxVerticle;
import org.oddox.config.AppConfig;
import org.oddox.config.Application;
import org.oddox.database.CouchDb;

/**
 * Unit tests for RootAction
 */
@RunWith(JUnit4.class)
public class RootActionTests {

    @InjectMocks
    private RootAction action;

    @Before
    public void beforeEachTest() {
        MockitoAnnotations.initMocks(this);
        AppConfig config;
        try {
            config = Application.loadSettingsFromFile(OddoxVerticle.APP_PROP_FILE);
            Application.setAppConfig(config);
            Application.setDatabaseService(
                    new CouchDb(Application.loadDatabase(System.getenv(), OddoxVerticle.DB_PROP_FILE)));
        } catch (IOException e) {
            fail("Unexpected IOException: " + e.getMessage());
        }
    }

    @Test
    public void constructor() {
        assertTrue(action != null);
    }

    @Test
    public void variables() {
        action.setOddox("Welcome");
        assertEquals("Welcome", action.getOddox());
        action.setVersion("1.0.0");
        assertEquals("1.0.0", action.getVersion());
        action.setMessage("message");
        assertEquals("message", action.getMessage());
        action.setError("error");
        assertEquals("error", action.getError());
        action.setData(null);
        assertNull(action.getData());
    }
}
