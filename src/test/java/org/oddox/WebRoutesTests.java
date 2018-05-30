package org.oddox;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;

import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.ext.web.Router;

@RunWith(VertxUnitRunner.class)
public class WebRoutesTests {

    @Test
    public void getNewRouter() {

        Vertx vertx = Vertx.vertx();
        Router router = WebRoutes.newRouter(vertx);

        assertNotNull(router.get());
        assertNotNull(router.get("/blog"));
    }
}
