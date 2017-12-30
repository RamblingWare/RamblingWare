package org.oddox.action.interceptor;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.oddox.action.interceptor.HeaderInterceptor;

/**
 * Unit tests for HeaderInterceptor
 * 
 * @author amdelamar
 * @since 1.0.0
 */
@RunWith(JUnit4.class)
public class HeaderInterceptorTests {

    @InjectMocks
    private HeaderInterceptor interceptor;

    @Before
    public void beforeEachTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void constructor() {
        assertTrue(interceptor != null);
    }

    @Test
    public void variables() {
        interceptor.init();
        interceptor.destroy();
    }
}
