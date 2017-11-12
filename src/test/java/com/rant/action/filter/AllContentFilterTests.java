package com.rant.action.filter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import com.rant.action.filter.AllContentFilter;

/**
 * Unit tests for AllContentFilter
 * 
 * @author amdelamar
 * @since 1.0.0
 */
@RunWith(JUnit4.class)
public class AllContentFilterTests {

    @InjectMocks
    private AllContentFilter filter;

    @Before
    public void beforeEachTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void variables() {
        filter.init(null);
        filter.destroy();
    }
}
