package org.oddox.database;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.oddox.config.AppConfig;
import org.oddox.config.Application;
import org.oddox.database.CouchDbSetup;
import org.oddox.database.Database;
import org.oddox.objects.Author;
import org.oddox.objects.Post;
import org.oddox.objects.Role;

/**
 * Unit tests for CouchDBSetup
 * 
 * @author amdelamar
 * @since 1.0.0
 */
@RunWith(JUnit4.class)
public class CouchDbSetupTests {

    @InjectMocks
    private Database database;

    @InjectMocks
    private CouchDbSetup setup;

    @Before
    public void beforeEachTest() {
        MockitoAnnotations.initMocks(this);

        assertNotNull(setup);
        assertNotNull(database);
        database.setHost("127.0.0.1");
        database.setPort("5984");
        database.setUsername("admin");
        database.setPassword("admin");
        database.setUrl("http://127.0.0.1:5984/");
        setup.setDatabase(database);
        
        AppConfig config = Application.loadSettingsFromFile(Application.getPropFile());
        Application.setAppConfig(config);
        assertNotNull(Application.getAppConfig());
    }

    @Test
    public void construct() {
        assertEquals(database, setup.getDatabase());
        setup = new CouchDbSetup(database);
        assertEquals(database, setup.getDatabase());
    }

    @Test
    public void defaults() {
        Author author = setup.getDefaultAuthor();
        assertNotNull(author);
        assertTrue(author.getName().equalsIgnoreCase(Application.getString("default.username")));
        assertNotNull(author.get_Id());
        assertNotNull(author.getName());

        Post post = setup.getDefaultPost();
        assertNotNull(post);
        assertTrue(post.getAuthorId().equalsIgnoreCase(Application.getString("default.username")));
        assertNotNull(post.get_Id());
        assertNotNull(post.getTitle());

        List<Role> roles = setup.getDefaultRoles();
        assertNotNull(roles);
        assertTrue(roles.size() > 1);
    }

    @Test
    public void checks() {
        assertNotNull(setup.isHttps());
    }

}
