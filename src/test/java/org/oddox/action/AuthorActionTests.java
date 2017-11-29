package org.oddox.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.oddox.action.AuthorAction;
import org.oddox.objects.Author;

/**
 * Unit tests for AuthorAction
 * 
 * @author amdelamar
 * @since 1.0.0
 */
@RunWith(JUnit4.class)
public class AuthorActionTests {

    @InjectMocks
    private AuthorAction action;

    @Before
    public void beforeEachTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void variables() {
        action.setAuthor(null);
        assertNull(action.getAuthor());

        Author author = new Author("admin");
        action.setAuthor(author);
        assertEquals("admin", action.getAuthor().get_Id());

        action.setServletRequest(null);
        action.setServletResponse(null);
    }
}
