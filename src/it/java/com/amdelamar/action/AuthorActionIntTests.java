package com.amdelamar.action;

import com.amdelamar.objects.Author;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Integration tests for AuthorAction
 */
@RunWith(JUnit4.class)
public class AuthorActionIntTests {

    @InjectMocks
    private AuthorAction handle;

    @Before
    public void beforeEachTest() {
        MockitoAnnotations.initMocks(this);
        assertTrue(handle != null);
    }

    @Test
    public void variables() {
        handle.setAuthor(null);
        assertNull(handle.getAuthor());

        Author author = new Author("admin");
        handle.setAuthor(author);
        Assert.assertEquals("admin", handle.getAuthor()
                .get_Id());
    }
}
