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
 * Unit tests for CategoryAction
 */
@RunWith(JUnit4.class)
public class CategoryActionTests {

    @InjectMocks
    private CategoryAction handle;

    @Before
    public void beforeEachTest() {
        MockitoAnnotations.initMocks(this);
        assertTrue(handle != null);
    }

    @Test
    public void variables() {
        handle.setPosts(null);
        assertNull(handle.getPosts());

        handle.setCategory("Meta");
        assertEquals("Meta", handle.getCategory());
        handle.setPage(2);
        assertEquals(2, handle.getPage());
        handle.setNextPage(3);
        assertEquals(3, handle.getNextPage());
        handle.setPrevPage(1);
        assertEquals(1, handle.getPrevPage());
        handle.setTotalPages(3);
        assertEquals(3, handle.getTotalPages());

        Post post = new Post("newpost");
        List<Post> list = new ArrayList<Post>();
        list.add(post);
        handle.setPosts(list);
        assertEquals("newpost", handle.getPosts()
                .get(0)
                .get_Id());
    }
}
