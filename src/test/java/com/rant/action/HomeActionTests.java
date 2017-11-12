package com.rant.action;

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

import com.rant.objects.Post;

/**
 * Unit tests for HomeAction
 * 
 * @author amdelamar
 * @since 1.0.0
 */
@RunWith(JUnit4.class)
public class HomeActionTests {

    @InjectMocks
    private HomeAction action;

    @Before
    public void beforeEachTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void variables() {
        action.setPosts(null);
        assertNull(action.getPosts());

        Post post = new Post("newpost");
        List<Post> list = new ArrayList<Post>();
        list.add(post);
        action.setPosts(list);
        assertEquals("newpost", action.getPosts().get(0).get_Id());

        action.setServletRequest(null);
        action.setServletResponse(null);
    }
}
