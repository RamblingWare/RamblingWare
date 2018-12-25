package com.amdelamar.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import com.amdelamar.objects.Author;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

/**
 * Unit tests for AuthorsAction
 */
@RunWith(JUnit4.class)
public class AuthorsActionTests {

    @InjectMocks
    private AuthorsAction handle;

    @Before
    public void beforeEachTest() {
        MockitoAnnotations.initMocks(this);
        assertTrue(handle != null);
    }

    @Test
    public void variables() {
        handle.setAuthors(null);
        assertNull(handle.getAuthors());

        Author author = new Author("admin");
        List<Author> list = new ArrayList<Author>();
        list.add(author);
        handle.setAuthors(list);
        assertEquals("admin", handle.getAuthors()
                .get(0)
                .get_Id());
    }
}
