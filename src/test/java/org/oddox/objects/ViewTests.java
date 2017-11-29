package org.oddox.objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.oddox.objects.View;

/**
 * Unit tests for View Object
 * 
 * @author amdelamar
 * @since 1.0.0
 */
@RunWith(JUnit4.class)
public class ViewTests {

    @Test
    public void view() {
        View view = new View();
        view.set_Id("blogpost");
        assertEquals("blogpost", view.get_Id());
        view.set_Rev("1");
        assertEquals("1", view.get_Rev());
        view.setCount(1000l);
        assertEquals(1000l, view.getCount());
        assertEquals("1k", view.getCountReadable());
        view.setSession(1000l);
        assertEquals(1000l, view.getSession());
        assertEquals("1k", view.getSessionReadable());
        assertNotNull(view.toString());

        View view2 = new View();
        view2.set_Id("blogpost");
        assertEquals("blogpost", view2.get_Id());

        assertTrue(view.compareTo(view2) == 0);

        View view3 = new View();
        view3.set_Id("blogpost3");
        assertEquals("blogpost3", view3.get_Id());

        assertTrue(view.compareTo(view3) != 0);
    }

}
