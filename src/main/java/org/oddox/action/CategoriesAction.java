package org.oddox.action;

import java.util.List;

import org.oddox.action.interceptor.ArchiveInterceptor;
import org.oddox.objects.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cloudant.client.org.lightcouch.NoDocumentException;

import io.vertx.core.Handler;
import io.vertx.reactivex.ext.web.RoutingContext;

/**
 * Categories action class
 * 
 * @author Austin Delamar
 * @date 4/30/2017
 */
public class CategoriesAction implements Handler<RoutingContext> {

    private static Logger logger = LoggerFactory.getLogger(CategoriesAction.class);
    private List<Category> categories = null;

    /**
     * Returns list of categories.
     */
    @Override
    public void handle(RoutingContext context) {

        // /category
        try {
            // gather categories
            categories = ArchiveInterceptor.getArchiveCategories();

            if (categories == null || categories.isEmpty()) {
                categories = null;
                throw new NoDocumentException("No categories found");
            }
            return SUCCESS;

        } catch (NoDocumentException nfe) {
            return NONE;
        } catch (Exception e) {
            addActionError("Error: " + e.getClass()
                    .getName() + ". Please try again later.");
            e.printStackTrace();
            return ERROR;
        }
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
