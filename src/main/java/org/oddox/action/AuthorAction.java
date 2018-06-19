package org.oddox.action;

import org.oddox.OddoxVerticle;
import org.oddox.config.Application;
import org.oddox.config.Utils;
import org.oddox.objects.Author;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.Handler;
import io.vertx.reactivex.ext.web.RoutingContext;
import io.vertx.reactivex.ext.web.templ.FreeMarkerTemplateEngine;
import io.vertx.reactivex.ext.web.templ.TemplateEngine;

/**
 * Author action class
 * 
 * @author amdelamar
 * @date 10/23/2016
 */
public class AuthorAction implements Handler<RoutingContext> {

    private final static Logger logger = LoggerFactory.getLogger(AuthorAction.class);
    private final static TemplateEngine ENGINE = FreeMarkerTemplateEngine.create();
    private Author author;

    /**
     * Returns author details.
     */
    @Override
    public void handle(RoutingContext context) {
        
        // Don't handle if response ended
        if(context.response().ended()) {
            context.next();
            return;
        }

        // /author/person-name
        String templateFile = "author/author.ftl";
        String uri = context.request().getParam("author");

        if (uri != null && !uri.isEmpty()) {
            // lower-case no matter what
            uri = uri.toLowerCase();
            uri = Utils.removeBadChars(uri);

            // search in db for author
            try {
                author = Application.getDatabaseService()
                        .getAuthor(uri, false);

                if (author == null) {
                    logger.error("Author '" + uri + "' not found. Please try again.");
                    templateFile = "author/author.ftl";
                }

            } catch (Exception e) {
                logger.error("Error: " + e.getClass()
                        .getName() + ". Please try again later.", e);
                templateFile = "/error/error.ftl";
            }
        } else {
            logger.error("Author '" + uri + "' not found. Please try again.");
            templateFile = "author/author.ftl";
        }

        // Bind Context
        context.put("author", author);
        
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

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Author getAuthor() {
        return author;
    }
}
