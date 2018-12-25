package com.amdelamar.action;

import java.util.List;

import com.amdelamar.action.interceptor.ArchiveInterceptor;
import com.amdelamar.objects.Category;
import com.amdelamar.OddoxVerticle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cloudant.client.org.lightcouch.NoDocumentException;

import io.vertx.core.Handler;
import io.vertx.reactivex.ext.web.RoutingContext;
import io.vertx.reactivex.ext.web.templ.FreeMarkerTemplateEngine;
import io.vertx.reactivex.ext.web.templ.TemplateEngine;

/**
 * Categories action class
 * 
 * @author amdelamar
 * @date 4/30/2017
 */
public class CategoriesAction implements Handler<RoutingContext> {

    private final static Logger logger = LoggerFactory.getLogger(CategoriesAction.class);
    private final static TemplateEngine ENGINE = FreeMarkerTemplateEngine.create();
    private List<Category> categories = null;

    /**
     * Returns list of categories.
     */
    @Override
    public void handle(RoutingContext context) {
        
        // Don't handle if response ended
        if(context.response().ended()) {
            context.next();
            return;
        }

        // /category
        String templateFile = "category/categories.ftl";
        try {
            // gather categories
            categories = ArchiveInterceptor.getArchiveCategories();

            if (categories == null || categories.isEmpty()) {
                categories = null;
                throw new NoDocumentException("No categories found");
            }

        } catch (NoDocumentException nfe) {
            templateFile = "category/categories.ftl";
        } catch (Exception e) {
            logger.error("Error: " + e.getClass()
                    .getName() + ". Please try again later.", e);
            templateFile = "/error/error.ftl";
        }
        
        // Bind Context
        context.put("categories", categories);

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

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
