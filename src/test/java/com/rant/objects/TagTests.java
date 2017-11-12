package com.rant.objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Unit tests for Tag Object
 * 
 * @author amdelamar
 * @since 1.0.0
 */
@RunWith(JUnit4.class)
public class TagTests {

    @Test
    public void tag() {
        Tag tag = new Tag();
        tag.setName("Meta");
        assertEquals("Meta", tag.getName());
        tag.setCount(10);
        assertEquals(10, tag.getCount());
        assertNotNull(tag.toString());

        Tag tag2 = new Tag();
        tag2.setName("Meta");
        assertEquals("Meta", tag2.getName());

        assertTrue(tag.compareTo(tag2) == 0);

        Tag tag3 = new Tag();
        tag3.setName("New");
        assertEquals("New", tag3.getName());

        assertTrue(tag.compareTo(tag3) != 0);
    }

}
