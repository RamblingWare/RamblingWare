package com.amdelamar.action.interceptor;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

/**
 * Unit tests for DynamicContentInterceptor
 */
@RunWith(JUnit4.class)
public class DynamicContentInterceptorTests {

    @InjectMocks
    private DynamicContentInterceptor handle;

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
