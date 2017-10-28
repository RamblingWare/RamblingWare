package com.rant.action.api;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Unit tests for LoginAction
 * 
 * @author amdelamar
 * @since 1.0.0
 */
@RunWith(JUnit4.class)
public class LoginTests {

    private LoginAction action;

    @Before
    public void beforeEachTest() {
        action = new LoginAction();
        action.setSession(new HashMap<String, Object>());
    }

    @Test
    public void parameters() {

        action.setUsername("admin");
        action.setPassword("admin");

        try {
            assertTrue(action.validParameters());
        } catch (Exception e) {
            fail(e.getMessage());
        }

        action.setUsername("admin");
        action.setPassword("");

        try {
            action.validParameters();
            fail("Failed to validate an empty password.");
        } catch (Exception e) {
            // good
        }

        action.setUsername("");
        action.setPassword("admin");

        try {
            action.validParameters();
            fail("Failed to validate an empty username.");
        } catch (Exception e) {
            // good
        }
    }

    @Test
    public void lockedOut() throws Exception {
        
        int maxAttempts = action.maxAttempts;

        // increment attempts up to limit
        for (int i = 1; i < maxAttempts; i++) {
            assertFalse(action.isLockedOut());
        }

        try {
            action.isLockedOut();
            fail("Failed to not lock out after "+maxAttempts+" failed attempts.");
        } catch (Exception e) {
            // good
        }
    }
}
