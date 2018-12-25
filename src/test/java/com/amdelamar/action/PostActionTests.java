package com.amdelamar.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.amdelamar.objects.Post;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

/**
 * Unit tests for PostAction
 */
@RunWith(JUnit4.class)
public class PostActionTests {

    @InjectMocks
    private PostAction handle;

    @Before
    public void beforeEachTest() {
        MockitoAnnotations.initMocks(this);
        assertTrue(handle != null);
    }

    @Test
    public void variables() {
        handle.setPost(null);
        assertNull(handle.getPost());

        Post post = new Post("newpost");
        handle.setPost(post);
        assertEquals("newpost", handle.getPost()
                .get_Id());
    }
}
