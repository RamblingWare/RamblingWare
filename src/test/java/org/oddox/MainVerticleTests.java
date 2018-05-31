package org.oddox;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

@RunWith(JUnit4.class)
public class MainVerticleTests {

    @InjectMocks
    private MainVerticle verticle;

    @Before
    public void beforeEachTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void constructor() {
        assertTrue(verticle != null);
    }

    @Test
    public void properties() {
        assertNotNull(MainVerticle.APP_PROP_FILE);
        assertTrue(MainVerticle.APP_PROP_FILE.endsWith(".properties"));

        assertNotNull(MainVerticle.DB_PROP_FILE);
        assertTrue(MainVerticle.DB_PROP_FILE.endsWith(".properties"));
    }
}
