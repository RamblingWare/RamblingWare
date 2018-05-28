package org.oddox.action;

import org.oddox.MainVerticle;
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
 * @author Austin Delamar
 * @date 10/23/2016
 */
public class AuthorAction implements Handler<RoutingContext> {

    private static Logger logger = LoggerFactory.getLogger(AuthorAction.class);
    private final TemplateEngine ENGINE = FreeMarkerTemplateEngine.create();
    private Author author;
    private String uri;

    /**
     * Returns author details.
     */
    @Override
    public void handle(RoutingContext context) {

        // /author/person-name
        String templateFile = "author/author.ftl";
        String uriTemp = context.normalisedPath();
        if (uri == null && uriTemp.startsWith("/author/")) {
            uri = Utils.removeBadChars(uriTemp.substring(8, uriTemp.length()));
        }

        if (uri != null && uri.length() > 0) {
            // lower-case no matter what
            uri = uri.toLowerCase();

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

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Author getAuthor() {
        return author;
    }
}
