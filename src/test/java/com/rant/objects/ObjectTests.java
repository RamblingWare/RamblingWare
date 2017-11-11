package com.rant.objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

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
        author.set_Id("admin");
        assertEquals("admin", author.get_Id());
        assertEquals("admin", author.getUsername());
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
        author.setRole(new Role("admin"));
        assertEquals("admin", author.getRole().get_Id());
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
        post.setFeatured(true);
        assertTrue(post.isFeatured());
        post.setPublished(true);
        assertTrue(post.isPublished());
        post.setCategory("Meta");
        assertEquals("Meta", post.getCategory());
        post.setAuthor_id("author_1");
        assertEquals("author_1", post.getAuthor_id());
        post.setAuthor(new Author("author_1"));
        assertEquals("author_1", post.getAuthor().get_Id());
        post.setView(new View());
        assertNotNull(post.getView());
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

    @Test
    public void category() {
        Category cat = new Category();
        cat.set_Id("12345");
        assertEquals("12345", cat.get_Id());
        cat.set_Rev("1");
        assertEquals("1", cat.get_Rev());
        cat.setName("Meta");
        assertEquals("Meta", cat.getName());
        cat.setCount(10);
        assertEquals(10, cat.getCount());
        assertNotNull(cat.toString());

        Category cat2 = new Category();
        cat2.setName("Meta");
        assertEquals("Meta", cat2.getName());

        assertTrue(cat.compareTo(cat2) == 0);

        Category cat3 = new Category();
        cat3.setName("New");
        assertEquals("New", cat3.getName());

        assertTrue(cat.compareTo(cat3) != 0);
    }

    @Test
    public void tag() {
        Tag tag = new Tag();
        tag.set_Id("12345");
        assertEquals("12345", tag.get_Id());
        tag.set_Rev("1");
        assertEquals("1", tag.get_Rev());
        tag.setName("Meta");
        assertEquals("Meta", tag.getName());
        tag.setCount(10);
        assertEquals(10, tag.getCount());
        assertNotNull(tag.toString());

        Tag tag2 = new Tag();
        tag2.setName("Meta");
        assertEquals("Meta", tag2.getName());

        assertTrue(tag.compareTo(tag2) == 0);

        Tag tag3 = new Tag();
        tag3.setName("New");
        assertEquals("New", tag3.getName());

        assertTrue(tag.compareTo(tag3) != 0);
    }

    @Test
    public void year() {
        Year yr = new Year();
        yr.setName("2017");
        assertEquals("2017", yr.getName());
        yr.setCount(10);
        assertEquals(10, yr.getCount());
        assertNotNull(yr.toString());

        Year yr2 = new Year();
        yr2.setName("2017");
        assertEquals("2017", yr2.getName());

        assertTrue(yr.compareTo(yr2) == 0);

        Year yr3 = new Year();
        yr3.setName("2018");
        assertEquals("2018", yr3.getName());

        assertTrue(yr.compareTo(yr3) != 0);
    }

    @Test
    public void role() {
        Role role = new Role("author");
        role.setName("Author");
        assertEquals("Author", role.getName());
        role.set_Id("author");
        assertEquals("author", role.get_Id());
        role.set_Rev("1");
        assertEquals("1", role.get_Rev());
        role.setDescription("Hello World");
        assertEquals("Hello World", role.getDescription());
        role.setPublic(false);
        role.setPostsCreate(false);
        role.setPostsEdit(true);
        role.setPostsEditOthers(true);
        role.setPostsSeeHidden(true);
        role.setPostsDelete(true);
        role.setUsersCreate(true);
        role.setUsersEdit(true);
        role.setUsersEditOthers(true);
        role.setUsersDelete(true);
        role.setRolesCreate(true);
        role.setRolesEdit(true);
        role.setRolesDelete(true);
        role.setPagesCreate(true);
        role.setPagesEdit(true);
        role.setPagesDelete(true);
        role.setCommentsCreate(false);
        role.setCommentsEdit(false);
        role.setCommentsEditOthers(false);
        role.setCommentsDelete(false);
        role.setSettingsCreate(true);
        role.setSettingsEdit(true);
        role.setSettingsDelete(true);
        assertTrue(!role.isPublic());
        assertTrue(!role.isPostsCreate());
        assertTrue(role.isPostsEdit());
        assertTrue(role.isPostsEditOthers());
        assertTrue(role.isPostsSeeHidden());
        assertTrue(role.isPostsDelete());
        assertTrue(role.isUsersCreate());
        assertTrue(role.isUsersEdit());
        assertTrue(role.isUsersEditOthers());
        assertTrue(role.isUsersDelete());
        assertTrue(role.isRolesCreate());
        assertTrue(role.isRolesEdit());
        assertTrue(role.isRolesDelete());
        assertTrue(role.isPagesCreate());
        assertTrue(role.isPagesEdit());
        assertTrue(role.isPagesDelete());
        assertTrue(!role.isCommentsCreate());
        assertTrue(!role.isCommentsEdit());
        assertTrue(!role.isCommentsEditOthers());
        assertTrue(!role.isCommentsDelete());
        assertTrue(role.isSettingsCreate());
        assertTrue(role.isSettingsEdit());
        assertTrue(role.isSettingsDelete());
        assertNotNull(role.toString());

        Role role2 = new Role("author");
        role2.setName("Author");
        assertEquals("Author", role2.getName());

        assertTrue(role.compareTo(role2) == 0);

        Role role3 = new Role("admin");
        role3.setName("Admin");
        assertEquals("Admin", role3.getName());

        assertTrue(role.compareTo(role3) != 0);
    }

    @Test
    public void view() {
        View view = new View();
        view.set_Id("blogpost");
        assertEquals("blogpost", view.get_Id());
        view.set_Rev("1");
        assertEquals("1", view.get_Rev());
        view.setCount(1000l);
        assertEquals(1000l, view.getCount());
        assertEquals("1k", view.getCountReadable());
        view.setSession(1000l);
        assertEquals(1000l, view.getSession());
        assertEquals("1k", view.getSessionReadable());
        assertNotNull(view.toString());

        View view2 = new View();
        view2.set_Id("blogpost");
        assertEquals("blogpost", view2.get_Id());

        assertTrue(view.compareTo(view2) == 0);

        View view3 = new View();
        view3.set_Id("blogpost3");
        assertEquals("blogpost3", view3.get_Id());

        assertTrue(view.compareTo(view3) != 0);
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
        db.setAdminParty(false);
        assertFalse(db.isAdminParty());
        assertNotNull(db.toString());

        Database db2 = new Database();
        db2.setUrl("http://127.0.0.1:5984");
        db2.setHost("127.0.0.1");
        db2.setPort("5984");
        db2.setUsername("admin");
        db2.setPassword("passwd");
        assertTrue(db.compareTo(db2) == 0);

        Database db3 = new Database();
        db3.setUrl("https://127.0.0.1:6984");
        db3.setHost("127.0.0.1");
        db3.setPort("6984");
        db3.setUsername("admin");
        db3.setPassword("passwd");
        assertTrue(db.compareTo(db3) != 0);
    }

    @Test
    public void databaseUser() {
        DatabaseUser user = new DatabaseUser();
        user.set_Id("admin");
        user.set_Rev("1");
        assertEquals("1", user.get_Rev());
        user.setName("admin");
        assertEquals("org.couchdb.user:admin", user.get_Id());
        user.setPassword("passwd");
        assertEquals("passwd", user.getPassword());
        user.setRoles(new String[]{"admin"});
        assertEquals("admin", user.getRoles()[0]);
        user.setType("user");
        assertEquals("user", user.getType());
        assertNotNull(user.toString());

        DatabaseUser user2 = new DatabaseUser("org.couchdb.user:admin");
        user2.setName("admin");
        assertTrue(user.compareTo(user2) == 0);

        DatabaseUser user3 = new DatabaseUser("author");
        user3.setName("author");
        assertTrue(user.compareTo(user3) != 0);
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
        assertNotNull(em.toString());

        Email em2 = new Email("to", "from", "subject", "message");
        assertEquals("to", em2.getTo());
        assertEquals("from", em2.getFrom());
        assertEquals("subject", em2.getSubject());
        assertEquals("message", em2.getMessage());
    }

    @Test
    public void config() {
        AppConfig config = new AppConfig();
        config.set_Id("APPCONFIG");
        config.set_Rev("1");
        assertEquals("APPCONFIG", config.get_Id());
        assertEquals("1", config.get_Rev());
        assertNotNull(config.toString());

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("key", "value");
        config.setSettings(map);
        assertNotNull(config.getSettings());
        assertEquals("value", config.getSettings().get("key"));
    }

    @Test
    public void firewall() {
        AppFirewall fw = new AppFirewall();
        fw.set_Id("APPFIREWALL");
        fw.set_Rev("1");
        fw.setEnabled(true);
        assertEquals("APPFIREWALL", fw.get_Id());
        assertEquals("1", fw.get_Rev());
        assertTrue(fw.isEnabled());
        assertNotNull(fw.toString());

        List<String> wlist = new ArrayList<String>();
        wlist.add("0.0.0.0");
        fw.setWhitelist(wlist);
        assertTrue(fw.getWhitelist().contains("0.0.0.0"));

        List<String> blist = new ArrayList<String>();
        blist.add("8.8.8.8");
        fw.setBlacklist(blist);
        assertTrue(fw.getBlacklist().contains("8.8.8.8"));
    }

}
