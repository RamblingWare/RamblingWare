package com.amdelamar.database;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Unit tests for DatabaseUser Object
 */
@RunWith(JUnit4.class)
public class DatabaseUserTests {

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
        user.setRoles(new String[] { "admin" });
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

}
