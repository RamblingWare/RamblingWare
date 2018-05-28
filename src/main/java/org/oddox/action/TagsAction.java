package org.oddox.action;

import java.util.List;

import org.oddox.MainVerticle;
import org.oddox.action.interceptor.ArchiveInterceptor;
import org.oddox.objects.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cloudant.client.org.lightcouch.NoDocumentException;

import io.vertx.core.Handler;
import io.vertx.reactivex.ext.web.RoutingContext;
import io.vertx.reactivex.ext.web.templ.FreeMarkerTemplateEngine;
import io.vertx.reactivex.ext.web.templ.TemplateEngine;

/**
 * Tags action class
 * 
 * @author Austin Delamar
 * @date 4/20/2017
 */
public class TagsAction implements Handler<RoutingContext> {

    private static Logger logger = LoggerFactory.getLogger(TagsAction.class);
    private final TemplateEngine ENGINE = FreeMarkerTemplateEngine.create();
    private List<Tag> tags = null;

    /**
     * Returns list of tags.
     */
    @Override
    public void handle(RoutingContext context) {

        // /tag
        String templateFile = "tag/tags.ftl";
        try {
            // gather tags
            tags = ArchiveInterceptor.getArchiveTags();

            if (tags == null || tags.isEmpty()) {
                tags = null;
                throw new NoDocumentException("No tags found");
            }

        } catch (NoDocumentException nfe) {
            templateFile = "tag/tags.ftl";
        } catch (Exception e) {
            logger.error("Error: " + e.getClass()
                    .getName() + ". Please try again later.", e);
            templateFile = "/error/error.ftl";
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

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
