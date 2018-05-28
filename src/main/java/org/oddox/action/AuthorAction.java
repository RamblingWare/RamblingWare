package org.oddox.action;

import org.oddox.config.Application;
import org.oddox.config.Utils;
import org.oddox.objects.Author;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.Handler;
import io.vertx.reactivex.ext.web.RoutingContext;

/**
 * Author action class
 * 
 * @author Austin Delamar
 * @date 10/23/2016
 */
public class AuthorAction implements Handler<RoutingContext> {

    private static Logger logger = LoggerFactory.getLogger(AuthorAction.class);
    private Author author;
    private String uri;

    /**
     * Returns author details.
     */
    @Override
    public void handle(RoutingContext context) {
        
        // /author/person-name
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

                if (author != null) {

                    return SUCCESS;
                } else {
                    System.err.println("Author '" + uri + "' not found. Please try again.");
                    return NONE;
                }

            } catch (Exception e) {
                addActionError("Error: " + e.getClass()
                        .getName() + ". Please try again later.");
                e.printStackTrace();
                return ERROR;
            }
        } else {
            System.err.println("Author '" + uri + "' not found. Please try again.");
            return NONE;
        }
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Author getAuthor() {
        return author;
    }
}
