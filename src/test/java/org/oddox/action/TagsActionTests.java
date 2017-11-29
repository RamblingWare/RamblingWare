package org.oddox.action;

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
import org.oddox.action.TagsAction;
import org.oddox.objects.Tag;

/**
 * Unit tests for TagsAction
 * 
 * @author amdelamar
 * @since 1.0.0
 */
@RunWith(JUnit4.class)
public class TagsActionTests {

    @InjectMocks
    private TagsAction action;

    @Before
    public void beforeEachTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void variables() {
        action.setTags(null);
        assertNull(action.getTags());

        Tag cat = new Tag();
        cat.setName("Meta");
        List<Tag> list = new ArrayList<Tag>();
        list.add(cat);
        action.setTags(list);
        assertEquals("Meta", action.getTags().get(0).getName());

        action.setServletRequest(null);
        action.setServletResponse(null);
    }
}
