package org.oddox.objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.oddox.objects.Year;

/**
 * Unit tests for Year Object
 * 
 * @author amdelamar
 * @since 1.0.0
 */
@RunWith(JUnit4.class)
public class YearTests {

    @Test
    public void year() {
        Year yr = new Year();
        yr.setName("2017");
        assertEquals("2017", yr.getName());
        yr.setCount(10);
        assertEquals(10, yr.getCount());
        assertNotNull(yr.toString());

        Year yr2 = new Year();
        yr2.setName("2017");
        assertEquals("2017", yr2.getName());

        assertTrue(yr.compareTo(yr2) == 0);

        Year yr3 = new Year();
        yr3.setName("2018");
        assertEquals("2018", yr3.getName());

        assertTrue(yr.compareTo(yr3) != 0);
    }

}
