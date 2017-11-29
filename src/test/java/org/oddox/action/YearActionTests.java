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
import org.oddox.action.YearAction;
import org.oddox.objects.Post;

/**
 * Unit tests for YearAction
 * 
 * @author amdelamar
 * @since 1.0.0
 */
@RunWith(JUnit4.class)
public class YearActionTests {

    @InjectMocks
    private YearAction action;

    @Before
    public void beforeEachTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void variables() {
        action.setPosts(null);
        assertNull(action.getPosts());
        
        action.setYear("Meta");
        assertEquals("Meta", action.getYear());
        action.setPage(2);
        assertEquals(2, action.getPage());
        action.setNextPage(true);
        assertTrue(action.isNextPage());
        action.setPrevPage(true);
        assertTrue(action.isPrevPage());

        Post post = new Post("newpost");
        List<Post> list = new ArrayList<Post>();
        list.add(post);
        action.setPosts(list);
        assertEquals("newpost", action.getPosts().get(0).get_Id());

        action.setServletRequest(null);
        action.setServletResponse(null);
    }
}
