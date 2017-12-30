package org.oddox.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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
    public void constructor() {
        assertTrue(action != null);
    }

    @Test
    public void variables() {
        action.setPosts(null);
        assertNull(action.getPosts());

        action.setYear("Meta");
        assertEquals("Meta", action.getYear());
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

        action.setServletRequest(null);
        action.setServletResponse(null);
        if(action.servletRequest != null) {
            fail("servletRequest is not expected value");
        }
    }
}
