package com.rant.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import com.rant.objects.Year;

/**
 * Unit tests for YearsAction
 * 
 * @author amdelamar
 * @since 1.0.0
 */
@RunWith(JUnit4.class)
public class YearsActionTests {

    @InjectMocks
    private YearsAction action;

    @Before
    public void beforeEachTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void variables() {
        action.setYears(null);
        assertNull(action.getYears());

        Year cat = new Year();
        cat.setName("Meta");
        List<Year> list = new ArrayList<Year>();
        list.add(cat);
        action.setYears(list);
        assertEquals("Meta", action.getYears().get(0).getName());

        action.setServletRequest(null);
        action.setServletResponse(null);
    }
}
