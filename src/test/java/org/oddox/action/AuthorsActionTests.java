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
import org.oddox.action.AuthorsAction;
import org.oddox.objects.Author;

/**
 * Unit tests for AuthorsAction
 * 
 * @author amdelamar
 * @since 1.0.0
 */
@RunWith(JUnit4.class)
public class AuthorsActionTests {

    @InjectMocks
    private AuthorsAction action;

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
        action.setAuthors(null);
        assertNull(action.getAuthors());

        Author author = new Author("admin");
        List<Author> list = new ArrayList<Author>();
        list.add(author);
        action.setAuthors(list);
        assertEquals("admin", action.getAuthors()
                .get(0)
                .get_Id());

        action.setServletRequest(null);
        action.setServletResponse(null);
        if(action.servletRequest != null) {
            fail("servletRequest is not expected value");
        }
    }
}
