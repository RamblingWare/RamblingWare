package com.amdelamar.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import com.amdelamar.objects.Year;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

/**
 * Unit tests for YearsAction
 */
@RunWith(JUnit4.class)
public class YearsActionTests {

    @InjectMocks
    private YearsAction handle;

    @Before
    public void beforeEachTest() {
        MockitoAnnotations.initMocks(this);
        assertTrue(handle != null);
    }

    @Test
    public void variables() {
        handle.setYears(null);
        assertNull(handle.getYears());

        Year cat = new Year();
        cat.setName("Meta");
        List<Year> list = new ArrayList<Year>();
        list.add(cat);
        handle.setYears(list);
        Assert.assertEquals("Meta", handle.getYears()
                .get(0)
                .getName());
    }
}
