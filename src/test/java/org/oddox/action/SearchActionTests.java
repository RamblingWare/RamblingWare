package org.oddox.action;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.oddox.action.SearchAction;

/**
 * Unit tests for SearchAction
 * 
 * @author amdelamar
 * @since 1.0.0
 */
@RunWith(JUnit4.class)
public class SearchActionTests {

    @InjectMocks
    private SearchAction action;

    @Before
    public void beforeEachTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void execute() {
        try {
            //action.setQ("java");
            //assertEquals("none", action.execute().toLowerCase());
        } catch (Exception e) {
            // ignore for now
        }
    }

    @Test
    public void variables() {

        action.setQ("java");
        assertEquals("java", action.getQ());

        action.setServletRequest(null);
        action.setServletResponse(null);
    }
}
