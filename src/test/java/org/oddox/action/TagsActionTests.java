package org.oddox.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.oddox.objects.Tag;

/**
 * Unit tests for TagsAction
 */
@RunWith(JUnit4.class)
public class TagsActionTests {

    @InjectMocks
    private TagsAction handle;

    @Before
    public void beforeEachTest() {
        MockitoAnnotations.initMocks(this);
        assertTrue(handle != null);
    }

    @Test
    public void variables() {
        handle.setTags(null);
        assertNull(handle.getTags());

        Tag cat = new Tag();
        cat.setName("Meta");
        List<Tag> list = new ArrayList<Tag>();
        list.add(cat);
        handle.setTags(list);
        assertEquals("Meta", handle.getTags()
                .get(0)
                .getName());
    }
}
