package org.oddox.action.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

/**
 * Unit tests for RootAction
 */
@RunWith(JUnit4.class)
public class RootActionTests {

    @InjectMocks
    private RootAction handle;

    @Before
    public void beforeEachTest() {
        MockitoAnnotations.initMocks(this);
        assertTrue(handle != null);
    }

    @Test
    public void variables() {
        handle.setOddox("Welcome");
        assertEquals("Welcome", handle.getOddox());
        handle.setVersion("1.0.0");
        assertEquals("1.0.0", handle.getVersion());
        handle.setMessage("message");
        assertEquals("message", handle.getMessage());
        handle.setError("error");
        assertEquals("error", handle.getError());
        handle.setData(null);
        assertNull(handle.getData());
    }
}
