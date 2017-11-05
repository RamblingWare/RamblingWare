package com.rant.objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.rant.objects.Author;
import com.rant.objects.Category;
import com.rant.objects.AppConfig;
import com.rant.objects.Database;
import com.rant.objects.Email;
import com.rant.objects.Post;
import com.rant.objects.Role;
import com.rant.objects.Tag;
import com.rant.objects.User;
import com.rant.objects.View;
import com.rant.objects.Year;

/**
 * Unit tests for Objects
 * 
 * @author amdelamar
 * @since 1.0.0
 */
@RunWith(JUnit4.class)
public class ObjectTests {

    @Test
    public void author() {
        Author author = new Author("admin");
        author.setName("admin");
        assertEquals("admin", author.getName());

        Author author2 = new Author("admin");
        author2.setName("admin");
        assertEquals("admin", author2.getName());
        
        assertTrue(author.compareTo(author2) == 0);
        
        Author author3 = new Author("author");
        author3.setName("author");
        assertEquals("author", author3.getName());

        assertTrue(author.compareTo(author3) != 0);
    }

    @Test
    public void post() {
        Post post = new Post("blogpost");
        post.setTitle("Blog Post");
        assertEquals("blogpost", post.get_Id());
        assertEquals("Blog Post", post.getTitle());

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

    @Test
    public void category() {
        Category cat = new Category();
        cat.setName("Meta");
        assertEquals("Meta", cat.getName());

        Category cat2 = new Category();
        cat2.setName("Meta");
        assertEquals("Meta", cat2.getName());

        assertTrue(cat.compareTo(cat2) == 0);
    }

    @Test
    public void tag() {
        Tag tag = new Tag();
        tag.setName("Meta");
        assertEquals("Meta", tag.getName());

        Tag tag2 = new Tag();
        tag2.setName("Meta");
        assertEquals("Meta", tag2.getName());

        assertTrue(tag.compareTo(tag2) == 0);
    }

    @Test
    public void year() {
        Year yr = new Year();
        yr.setName("2017");
        assertEquals("2017", yr.getName());

        Year yr2 = new Year();
        yr2.setName("2017");
        assertEquals("2017", yr2.getName());

        assertTrue(yr.compareTo(yr2) == 0);
    }

    @Test
    public void user() {
        User user = new User("admin");
        assertEquals("org.couchdb.user:admin", user.get_Id());
    }

    @Test
    public void role() {
        Role role = new Role("author");
        role.set_Id("author");
        assertEquals("author", role.get_Id());
    }

    @Test
    public void database() {
        Database db = new Database();
        db.setUrl("http://127.0.0.1:5984");
        assertEquals("http://127.0.0.1:5984", db.getUrl());
        db.setHost("127.0.0.1");
        assertEquals("127.0.0.1", db.getHost());
        db.setPort("5984");
        assertEquals("5984", db.getPort());
        db.setUsername("admin");
        assertEquals("admin", db.getUsername());
        db.setPassword("passwd");
        assertEquals("passwd", db.getPassword());
    }

    @Test
    public void email() {
        Email em = new Email();
        em.setTo("to");
        em.setFrom("from");
        em.setSubject("subject");
        em.setMessage("message");
        assertEquals("to", em.getTo());
        assertEquals("from", em.getFrom());
        assertEquals("subject", em.getSubject());
        assertEquals("message", em.getMessage());
    }

    @Test
    public void config() {
        AppConfig config = new AppConfig();
        assertEquals("APPCONFIG", config.get_Id());
    }

    @Test
    public void view() {
        View view = new View();
        view.set_Id("blogpost");
        assertEquals("blogpost", view.get_Id());
    }

}
