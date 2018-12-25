package com.amdelamar;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

/**
 * Unit tests for OddoxVerticle
 */
@RunWith(JUnit4.class)
public class OddoxVerticleTests {

    @InjectMocks
    private OddoxVerticle verticle;

    @Before
    public void beforeEachTest() {
        MockitoAnnotations.initMocks(this);
        assertTrue(verticle != null);
    }

    @Test
    public void properties() {
        assertNotNull(OddoxVerticle.APP_PROP_FILE);
        assertTrue(OddoxVerticle.APP_PROP_FILE.endsWith(".properties"));

        assertNotNull(OddoxVerticle.DB_PROP_FILE);
        assertTrue(OddoxVerticle.DB_PROP_FILE.endsWith(".properties"));
    }
}
