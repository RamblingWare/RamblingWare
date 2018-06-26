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
 */
@RunWith(JUnit4.class)
public class ForgotActionTests {

    @InjectMocks
    private ForgotAction handle;

    @Before
    public void beforeEachTest() {
        MockitoAnnotations.initMocks(this);
        assertTrue(handle != null);
    }

    @Test
    public void parameters() {
        handle.defaults();
        handle.setEmail("bob@protonmail.com");
        handle.setReset(true);

        try {
            assertTrue(handle.validParameters());
        } catch (Exception e) {
            fail(e.getMessage());
        }

        handle.setEmail("");
        handle.setReset(true);

        try {
            handle.validParameters();
            fail("Failed to validate on empty email.");
        } catch (Exception e) {
            // good
        }

        handle.setEmail("bad@email@address");
        handle.setReset(true);

        try {
            handle.validParameters();
            fail("Failed to validate on bad email.");
        } catch (Exception e) {
            // good
        }

        handle.setCode("");

        try {
            handle.validParameters();
            fail("Failed to validate on bad code.");
        } catch (Exception e) {
            // good
        }

        handle.setCode("1234567");

        try {
            handle.validParameters();
            fail("Failed to validate on too long code.");
        } catch (Exception e) {
            // good
        }
    }

    @Test
    public void lockedOut() {
        handle.setSession(new HashMap<String, Object>());
        int maxAttempts = handle.maxAttempts;

        // increment attempts up to limit
        for (int i = 1; i < maxAttempts; i++) {
            assertFalse(handle.isLockedOut(null));
        }

        assertEquals(maxAttempts, handle.getAttempts() + 1);

        try {
            handle.isLockedOut(null);
            fail("Failed to not lock out after " + maxAttempts + " failed attempts.");
        } catch (Exception e) {
            // good
        }
    }

    @Test
    public void variables() {
        handle.setEmail("email@example.com");
        assertEquals("email@example.com", handle.getEmail());
        handle.setCode("123456");
        assertEquals("123456", handle.getCode());
        handle.setType("reset");
        assertEquals("reset", handle.getType());

        handle.setRemind(true);
        assertTrue(handle.isRemind());
        handle.setRecover(true);
        assertTrue(handle.isRecover());
        handle.setReset(true);
        assertTrue(handle.isReset());

        handle.setForgot("forgot");
        assertEquals("forgot", handle.getForgot());
        handle.setMessage("message");
        assertEquals("message", handle.getMessage());
        handle.setError("error");
        assertEquals("error", handle.getError());
        handle.setData(null);
        assertNull(handle.getData());

        handle.setSession(null);
    }
}
