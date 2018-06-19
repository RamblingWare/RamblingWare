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
import org.oddox.objects.Post;

/**
 * Unit tests for BlogAction
 */
@RunWith(JUnit4.class)
public class BlogActionTests {

    @InjectMocks
    private BlogAction action;

    @Before
    public void beforeEachTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void constructor() {
        assertTrue(action != null);
    }

    @Test
    public void variables() {
        action.setPosts(null);
        assertNull(action.getPosts());

        action.setPage(2);
        assertEquals(2, action.getPage());
        action.setNextPage(3);
        assertEquals(3, action.getNextPage());
        action.setPrevPage(1);
        assertEquals(1, action.getPrevPage());
        action.setTotalPages(3);
        assertEquals(3, action.getTotalPages());

        Post post = new Post("newpost");
        List<Post> list = new ArrayList<Post>();
        list.add(post);
        action.setPosts(list);
        assertEquals("newpost", action.getPosts()
                .get(0)
                .get_Id());
    }
}
