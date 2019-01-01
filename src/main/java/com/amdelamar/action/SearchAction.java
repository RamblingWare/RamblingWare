package com.amdelamar.action;

import com.amdelamar.config.Application;
import com.amdelamar.config.Utils;
import com.amdelamar.OddoxVerticle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.Handler;
import io.vertx.reactivex.ext.web.RoutingContext;
import io.vertx.reactivex.ext.web.templ.FreeMarkerTemplateEngine;
import io.vertx.reactivex.ext.web.templ.TemplateEngine;

/**
 * External Search action class
 * 
 * @author amdelamar
 * @date 5/9/2016
 */
public class SearchAction implements Handler<RoutingContext> {

    private final static Logger logger = LoggerFactory.getLogger(SearchAction.class);
    private final static TemplateEngine ENGINE = FreeMarkerTemplateEngine.create();
    private String q;

    /**
     * Forwards to search provider.
     */
    @Override
    public void handle(RoutingContext context) {
        
        // Don't handle if response ended
        if (context.response().ended() || context.response().closed()) {
            return;
        }

        String templateFile = "search.ftl";
        if (q != null && !q.isEmpty()) {
            // POST external search
            try {
                // redirect to search provider with the search text provided
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
        ENGINE.render(context, OddoxVerticle.TEMPLATES_DIR, templateFile, res -> {
            context.response().putHeader("content-type", "text/html;charset=UTF-8");
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
