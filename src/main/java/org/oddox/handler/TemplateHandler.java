package org.oddox.handler;

import io.vertx.core.Handler;
import io.vertx.reactivex.ext.web.RoutingContext;
import io.vertx.reactivex.ext.web.templ.FreeMarkerTemplateEngine;
import io.vertx.reactivex.ext.web.templ.TemplateEngine;

public class TemplateHandler implements Handler<RoutingContext> {

    private final TemplateEngine engine = FreeMarkerTemplateEngine.create();

    @Override
    public void handle(RoutingContext context) {

        // set variable
        context.put("greeting", "Hello from FreeMarkerTemplateEngine!");
        
        final String dir = System.getProperty("user.dir");
        
        engine.render(context, dir + "/webroot/templates/", "index.ftl", res -> {
            if (res.succeeded()) {
                context.response()
                        .end(res.result());
            } else {
                context.fail(res.cause());
            }
        });
    }

}
