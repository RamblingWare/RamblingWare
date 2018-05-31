package org.oddox.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

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
    public void constructor() {
        assertTrue(action != null);
    }

    @Test
    public void variables() {

        action.setQ("java");
        assertEquals("java", action.getQ());
    }
}
