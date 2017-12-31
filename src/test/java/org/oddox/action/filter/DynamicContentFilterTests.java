package org.oddox.action.filter;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.oddox.action.filter.DynamicContentFilter;

/**
 * Unit tests for DynamicContentFilter
 * 
 * @author amdelamar
 * @since 1.0.0
 */
@RunWith(JUnit4.class)
public class DynamicContentFilterTests {

    @InjectMocks
    private DynamicContentFilter filter;

    @Before
    public void beforeEachTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void constructor() {
        assertTrue(filter != null);
    }

    @Test
    public void variables() {
        filter.init(null);
        filter.destroy();
    }
}
