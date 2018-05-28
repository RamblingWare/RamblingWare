package org.oddox.action;

import java.util.List;

import org.oddox.MainVerticle;
import org.oddox.config.Application;
import org.oddox.objects.Author;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cloudant.client.org.lightcouch.NoDocumentException;

import io.vertx.core.Handler;
import io.vertx.reactivex.ext.web.RoutingContext;
import io.vertx.reactivex.ext.web.templ.FreeMarkerTemplateEngine;
import io.vertx.reactivex.ext.web.templ.TemplateEngine;

/**
 * Authors action class
 * 
 * @author Austin Delamar
 * @date 4/23/2017
 */
public class AuthorsAction implements Handler<RoutingContext> {

    private static Logger logger = LoggerFactory.getLogger(AuthorsAction.class);
    private final TemplateEngine ENGINE = FreeMarkerTemplateEngine.create();
    private List<Author> authors = null;

    /**
     * Returns list of authors.
     * 
     * @return Action String
     */
    @Override
    public void handle(RoutingContext context) {

        // /author
        String templateFile = "author/authors.ftl";
        try {
            // gather authors
            authors = Application.getDatabaseService()
                    .getAuthors(1, Application.getInt("resultsPerPage"), false);

            if (authors == null || authors.isEmpty()) {
                authors = null;
                throw new NoDocumentException("No authors found");
            }

        } catch (NoDocumentException nfe) {
            templateFile = "author/authors.ftl";
        } catch (Exception e) {
            logger.error("Error: " + e.getClass()
                    .getName() + ". Please try again later.", e);
            templateFile = "/error/error.ftl";
        }

        // Render template response
        ENGINE.render(context, MainVerticle.TEMPLATES_DIR, templateFile, res -> {
            context.response().putHeader("content-type", "text/html;charset=UTF-8");
            if (res.succeeded()) {
                context.response()
                        .end(res.result());
            } else {
                context.fail(res.cause());
            }
        });
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

}
