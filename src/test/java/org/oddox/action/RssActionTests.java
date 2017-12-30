package org.oddox.action;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.oddox.action.RssAction;

/**
 * Unit tests for RssAction
 * 
 * @author amdelamar
 * @since 1.0.0
 */
@RunWith(JUnit4.class)
public class RssActionTests {

    @InjectMocks
    private RssAction action;

    @Before
    public void beforeEachTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void constructor() {
        assertTrue(action != null);
    }

    @Test
    public void variables() {
        action.setServletRequest(null);
        action.setServletResponse(null);
        if(action.servletRequest != null) {
            fail("servletRequest is not expected value");
        }
    }
}
