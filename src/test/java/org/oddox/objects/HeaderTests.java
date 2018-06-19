package org.oddox.objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Unit tests for Header Object
 */
@RunWith(JUnit4.class)
public class HeaderTests {

    @Test
    public void header() {
        Header head = new Header("KEY", "VALUE");
        assertEquals("KEY", head.getKey());
        assertEquals("VALUE", head.getValue());

        head.setKey("Server");
        assertEquals("Server", head.getKey());
        head.setValue("Oddox");
        assertEquals("Oddox", head.getValue());
        assertNotNull(head.toString());
    }

}
