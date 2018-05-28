package org.oddox.action;

import org.oddox.MainVerticle;
import org.oddox.config.Application;
import org.oddox.config.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.Handler;
import io.vertx.reactivex.ext.web.RoutingContext;
import io.vertx.reactivex.ext.web.templ.FreeMarkerTemplateEngine;
import io.vertx.reactivex.ext.web.templ.TemplateEngine;

/**
 * External Search action class
 * 
 * @author Austin Delamar
 * @date 5/9/2016
 */
public class SearchAction implements Handler<RoutingContext> {

    private static Logger logger = LoggerFactory.getLogger(SearchAction.class);
    private final TemplateEngine ENGINE = FreeMarkerTemplateEngine.create();
    private String q;

    /**
     * Forwards to search provider.
     */
    @Override
    public void handle(RoutingContext context) {

        String templateFile = "search.ftl";
        if (q != null && !q.isEmpty()) {
            // POST external search
            try {
                // redirect to DuckDuckGo with the search text provided
                context.response()
                        .setStatusCode(302);
                context.response()
                        .putHeader("Location", Application.getString("searchProvider") + "site%3A"
                                + Application.getString("domain") + ' ' + q);
                context.response()
                        .end();
                return;

            } catch (Exception e) {
                logger.error("Error: " + e.getClass()
                        .getName() + ". Please try again later.", e);
                templateFile = "/error/error.ftl";
            }
        }
        // else GET search page

        // Render template response
        ENGINE.render(context, MainVerticle.TEMPLATES_DIR, templateFile, res -> {
            if (res.succeeded()) {
                context.response()
                        .end(res.result());
            } else {
                context.fail(res.cause());
            }
        });
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = Utils.removeNonAsciiChars(q);
    }
}
