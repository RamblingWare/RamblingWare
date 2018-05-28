package org.oddox.action;

import java.util.List;

import org.oddox.config.Application;
import org.oddox.objects.Author;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cloudant.client.org.lightcouch.NoDocumentException;

import io.vertx.core.Handler;
import io.vertx.reactivex.ext.web.RoutingContext;

/**
 * Authors action class
 * 
 * @author Austin Delamar
 * @date 4/23/2017
 */
public class AuthorsAction implements Handler<RoutingContext> {

    private static Logger logger = LoggerFactory.getLogger(AuthorsAction.class);
    private List<Author> authors = null;

    /**
     * Returns list of authors.
     * 
     * @return Action String
     */
    @Override
    public void handle(RoutingContext context) {

        // /author
        try {
            // gather authors
            authors = Application.getDatabaseService()
                    .getAuthors(1, Application.getInt("resultsPerPage"), false);

            if (authors == null || authors.isEmpty()) {
                authors = null;
                throw new NoDocumentException("No authors found");
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

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

}
