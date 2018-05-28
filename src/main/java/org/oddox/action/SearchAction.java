package org.oddox.action;

import java.io.IOException;

import org.apache.struts2.ServletActionContext;
import org.oddox.config.Application;
import org.oddox.config.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.Handler;
import io.vertx.reactivex.ext.web.RoutingContext;

/**
 * External Search action class
 * 
 * @author Austin Delamar
 * @date 5/9/2016
 */
public class SearchAction implements Handler<RoutingContext> {

    private static Logger logger = LoggerFactory.getLogger(SearchAction.class);
    private String q;

    /**
     * Forwards to search provider.
     */
    @Override
    public void handle(RoutingContext context) {

        if (q != null && !q.isEmpty()) {
            // POST external search
            try {
                // redirect to DuckDuckGo with the search text provided
                ServletActionContext.getResponse()
                        .sendRedirect(Application.getString("searchProvider") + "site%3A"
                                + Application.getString("domain") + ' ' + q);
                return SUCCESS;
            } catch (IOException e) {
                e.printStackTrace();
                addActionError("Error: " + e.getClass()
                        .getName() + ". Please try again later.");
                return ERROR;
            }
        } else {
            // GET search page
            return INPUT;
        }
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = Utils.removeNonAsciiChars(q);
    }
}
