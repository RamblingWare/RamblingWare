package org.oddox.action.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

/**
 * Unit tests for ForgotAction
 * 
 * @author amdelamar
 * @since 1.0.0
 */
@RunWith(JUnit4.class)
public class ForgotActionTests {

    @InjectMocks
    private ForgotAction action;

    @Before
    public void beforeEachTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void constructor() {
        assertTrue(action != null);
    }

    @Test
    public void defaults() {
        action.defaults();
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

        action.setCode("");

        try {
            action.validParameters();
            fail("Failed to validate on bad code.");
        } catch (Exception e) {
            // good
        }

        action.setCode("1234567");

        try {
            action.validParameters();
            fail("Failed to validate on too long code.");
        } catch (Exception e) {
            // good
        }
    }

    @Test
    public void lockedOut() {
        action.setSession(new HashMap<String, Object>());
        int maxAttempts = action.maxAttempts;

        // increment attempts up to limit
        for (int i = 1; i < maxAttempts; i++) {
            assertFalse(action.isLockedOut(null));
        }

        assertEquals(maxAttempts, action.getAttempts() + 1);

        try {
            action.isLockedOut(null);
            fail("Failed to not lock out after " + maxAttempts + " failed attempts.");
        } catch (Exception e) {
            // good
        }
    }

    @Test
    public void variables() {
        action.setEmail("email@example.com");
        assertEquals("email@example.com", action.getEmail());
        action.setCode("123456");
        assertEquals("123456", action.getCode());
        action.setType("reset");
        assertEquals("reset", action.getType());

        action.setRemind(true);
        assertTrue(action.isRemind());
        action.setRecover(true);
        assertTrue(action.isRecover());
        action.setReset(true);
        assertTrue(action.isReset());

        action.setForgot("forgot");
        assertEquals("forgot", action.getForgot());
        action.setMessage("message");
        assertEquals("message", action.getMessage());
        action.setError("error");
        assertEquals("error", action.getError());
        action.setData(null);
        assertNull(action.getData());

        action.setSession(null);
    }
}
