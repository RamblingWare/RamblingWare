package com.rant.objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Unit tests for Category Object
 * 
 * @author amdelamar
 * @since 1.0.0
 */
@RunWith(JUnit4.class)
public class CategoryTests {

    @Test
    public void category() {
        Category cat = new Category();
        cat.setName("Meta");
        assertEquals("Meta", cat.getName());
        cat.setCount(10);
        assertEquals(10, cat.getCount());
        assertNotNull(cat.toString());

        Category cat2 = new Category();
        cat2.setName("Meta");
        assertEquals("Meta", cat2.getName());

        assertTrue(cat.compareTo(cat2) == 0);

        Category cat3 = new Category();
        cat3.setName("New");
        assertEquals("New", cat3.getName());

        assertTrue(cat.compareTo(cat3) != 0);
    }

}
