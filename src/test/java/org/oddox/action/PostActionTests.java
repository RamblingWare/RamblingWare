package org.oddox.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.oddox.objects.Post;

/**
 * Unit tests for PostAction
 */
@RunWith(JUnit4.class)
public class PostActionTests {

    @InjectMocks
    private PostAction action;

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
        action.setPost(null);
        assertNull(action.getPost());

        Post post = new Post("newpost");
        action.setPost(post);
        assertEquals("newpost", action.getPost()
                .get_Id());
    }
}
