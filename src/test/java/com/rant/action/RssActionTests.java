package com.rant.action;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

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
    public void execute() {
        try {
            //assertEquals("none", action.execute().toLowerCase());
        } catch (Exception e) {
            // ignore for now
        }
    }

    @Test
    public void variables() {
        action.setServletRequest(null);
        action.setServletResponse(null);
    }
}
