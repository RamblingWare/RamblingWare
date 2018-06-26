package org.oddox;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.ext.web.Router;

/**
 * Unit tests for WebRoutes
 */
@RunWith(VertxUnitRunner.class)
public class WebRoutesTests {
    
    @InjectMocks
    private WebRoutes routes;

    @Before
    public void beforeEachTest() {
        MockitoAnnotations.initMocks(this);
        assertTrue(routes != null);
    }

    @Test
    public void getNewRouter() {

        Vertx vertx = Vertx.vertx();
        Router router = WebRoutes.newRouter(vertx);

        assertNotNull(router.get());
        assertNotNull(router.get("/blog"));
    }
}
