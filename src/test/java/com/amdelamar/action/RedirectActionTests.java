package com.amdelamar.action;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

/**
 * Unit tests for RedirectAction
 */
@RunWith(JUnit4.class)
public class RedirectActionTests {

    @InjectMocks
    private RedirectAction handle;

    @Before
    public void beforeEachTest() {
        MockitoAnnotations.initMocks(this);
        assertTrue(handle != null);
    }
    
    @Test
    public void test() {
        // TODO
        assertTrue(true);
    }
}
