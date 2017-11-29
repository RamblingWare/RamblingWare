package org.oddox.objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.oddox.config.Utils;
import org.oddox.objects.Author;
import org.oddox.objects.Post;
import org.oddox.objects.View;

/**
 * Unit tests for Post Object
 * 
 * @author amdelamar
 * @since 1.0.0
 */
@RunWith(JUnit4.class)
public class PostTests {

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
    public void post() {
        Post post = new Post("blogpost");
        post.set_Id("blogpost");
        assertEquals("blogpost", post.get_Id());
        post.set_Rev("1");
        assertEquals("1", post.get_Rev());
        post.setTitle("Blog Post");
        assertEquals("Blog Post", post.getTitle());
        post.setDescription("Hello World");
        assertEquals("Hello World", post.getDescription());
        post.setContent("Content goes here");
        assertEquals("Content goes here", post.getContent());
        post.setThumbnail("image");
        assertEquals("image", post.getThumbnail());
        post.setBanner("image");
        assertEquals("image", post.getBanner());
        post.setBannerCaption("caption");
        assertEquals("caption", post.getBannerCaption());
        post.setFeatured(true);
        assertTrue(post.isFeatured());
        post.setPublished(true);
        assertTrue(post.isPublished());
        post.setCategory("Meta");
        assertEquals("Meta", post.getCategory());
        post.setAuthorId("author_1");
        assertEquals("author_1", post.getAuthorId());
        post.setAuthor(new Author("author_1"));
        assertEquals("author_1", post.getAuthor().get_Id());
        post.setView(new View());
        List<String> list = new ArrayList<String>();
        list.add("Meta");
        post.setTags(list);
        assertEquals("Meta", post.getTags().get(0));
        assertNotNull(post.getView());

        post.setCreateDate(dateTime);
        assertEquals(dateTime, post.getCreateDate());
        assertEquals(readableDate, post.getCreateDateReadable());
        assertEquals(readableDateTime, post.getCreateDateTimeReadable());
        post.setModifyDate(dateTime);
        assertEquals(dateTime, post.getModifyDate());
        assertEquals(readableDate, post.getModifyDateReadable());
        assertEquals(readableDateTime, post.getModifyDateTimeReadable());
        post.setPublishDate(dateTime);
        assertEquals(dateTime, post.getPublishDate());
        assertEquals(readableDate, post.getPublishDateReadable());
        assertEquals(readableDateTime, post.getPublishDateTimeReadable());
        assertTrue(post.getPublishYear() > 0);
        assertNotNull(post.toString());

        Post post2 = new Post("blogpost");
        post2.setTitle("Blog Post");
        assertEquals("blogpost", post2.get_Id());
        assertEquals("Blog Post", post2.getTitle());

        assertTrue(post.compareTo(post2) == 0);

        Post post3 = new Post("fakepost");
        post3.setTitle("Fake Post");
        assertEquals("fakepost", post3.get_Id());
        assertEquals("Fake Post", post3.getTitle());

        assertTrue(post.compareTo(post3) != 0);
    }

}
