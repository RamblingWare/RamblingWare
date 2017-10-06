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
 * Unit tests for ForgotAction
 * 
 * @author amdelamar
 * @since 1.0.0
 */
@RunWith(JUnit4.class)
public class ForgotTests {
    
    private ForgotAction action;
    
    @Before
    public void beforeEachTest() {
        action = new ForgotAction();
        action.setSession(new HashMap<String, Object>());
    }
    
    @Test
    public void parameters() {
        
        action.setEmail("bob@protonmail.com");
        action.setReset(true);
        
        try {
            assertTrue(action.validParameters());
        } catch (Exception e) {
            fail(e.getMessage());
        }
        
        action.setEmail("");
        action.setReset(true);
        
        try {
            action.validParameters();
            fail("Failed to validate on empty email.");
        } catch (Exception e) {
            // good
        }
        
        action.setEmail("bad@email@address");
        action.setReset(true);
        
        try {
            action.validParameters();
            fail("Failed to validate on bad email.");
        } catch (Exception e) {
            // good
        }
    }

    @Test
    public void lockedOut() throws Exception {
        
        // increment attempts up to limit
        for(int i=1; i<ForgotAction.MAX_ATTEMPTS; i++) {
            assertFalse(action.isLockedOut());
        }
        
        try {
            action.isLockedOut();
            fail("Failed to not lock out after 3 failed attempts.");
        } catch (Exception e) {
            // good
        }
    }
}
