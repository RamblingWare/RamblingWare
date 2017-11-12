package com.rant.action.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import com.rant.config.Application;
import com.rant.database.CouchDb;
import com.rant.objects.AppConfig;

/**
 * Unit tests for RootAction
 * 
 * @author amdelamar
 * @since 1.0.0
 */
@RunWith(JUnit4.class)
public class RootTests {

    @InjectMocks
    private RootAction action;

    @Before
    public void beforeEachTest() {
        MockitoAnnotations.initMocks(this);
        AppConfig config = Application.loadSettingsFromFile(Application.getPropFile());
        Application.setAppConfig(config);
        Application.setDatabaseService(new CouchDb(Application.loadDatabase()));
    }

    @Test
    public void execute() {
        assertEquals("none", action.execute().toLowerCase());
        assertEquals("Welcome", action.getRant());
        assertEquals(Application.getString("version"), action.getVersion());
        assertTrue(action.getData().containsKey("url"));
        assertTrue(action.getData().containsKey("name"));
    }
}
