package com.amdelamar.objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.amdelamar.config.Utils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Unit tests for Author Object
 */
@RunWith(JUnit4.class)
public class AuthorTests {

    private String dateTime;
    private String readableDate;
    private String readableDateTime;

    @Before
    public void beforeEachTest() {
        dateTime = Utils.getDateIso8601();
        readableDate = Utils.formatReadableDate(Utils.convertStringToDate(dateTime));
        readableDateTime = Utils.formatReadableDateTime(Utils.convertStringToDate(dateTime));
    }

    @Test
    public void author() {
        Author author = new Author("admin");
        author.set_Id("admin");
        assertEquals("admin", author.get_Id());
        assertEquals("admin", author.getUsername());
        assertEquals("admin", author.getUri());
        author.set_Rev("1");
        assertEquals("1", author.get_Rev());
        author.setName("admin");
        assertEquals("admin", author.getName());
        author.setEmail("email@example.com");
        assertEquals("email@example.com", author.getEmail());
        author.setDescription("Hello World");
        assertEquals("Hello World", author.getDescription());
        author.setContent("Author biography goes here");
        assertEquals("Author biography goes here", author.getContent());
        author.setThumbnail("image");
        assertEquals("image", author.getThumbnail());
        author.setRole("admin");
        assertEquals("admin", author.getRole());

        author.setCreateDate(dateTime);
        assertEquals(dateTime, author.getCreateDate());
        assertEquals(readableDate, author.getCreateDateReadable());
        assertEquals(readableDateTime, author.getCreateDateTimeReadable());
        author.setModifyDate(dateTime);
        assertEquals(dateTime, author.getModifyDate());
        assertEquals(readableDate, author.getModifyDateReadable());
        assertEquals(readableDateTime, author.getModifyDateTimeReadable());

        assertNotNull(author.toString());

        Author author2 = new Author("admin");
        author2.setName("admin");
        assertEquals("admin", author2.getName());

        assertTrue(author.compareTo(author2) == 0);

        Author author3 = new Author("author");
        author3.setName("author");
        assertEquals("author", author3.getName());

        assertTrue(author.compareTo(author3) != 0);
    }

}
