package com.rant.database;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

/**
 * Unit tests for CouchDB
 * 
 * @author amdelamar
 * @since 1.0.0
 */
@RunWith(JUnit4.class)
public class CouchDbTests {

    @InjectMocks
    private Database database;

    @InjectMocks
    private CouchDb couchdb;

    @Before
    public void beforeEachTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void construct() {
        assertNotNull(couchdb);
        assertNotNull(database);
        
        database.setHost("127.0.0.1");
        database.setPort("5984");
        database.setUsername("admin");
        database.setPassword("admin");
        database.setUrl("http://127.0.0.1:5984/");

        couchdb.setDatabase(database);
        assertEquals(database, couchdb.getDatabase());

        couchdb = new CouchDb(database);
        assertEquals(database, couchdb.getDatabase());
    }
}
