package com.rant.database;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.rant.database.Database;

/**
 * Unit tests for Database Object
 * 
 * @author amdelamar
 * @since 1.0.0
 */
@RunWith(JUnit4.class)
public class DatabaseTests {

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

}
