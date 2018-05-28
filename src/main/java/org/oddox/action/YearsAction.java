package org.oddox.action;

import java.util.List;

import org.oddox.action.interceptor.ArchiveInterceptor;
import org.oddox.objects.Year;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cloudant.client.org.lightcouch.NoDocumentException;

import io.vertx.core.Handler;
import io.vertx.reactivex.ext.web.RoutingContext;

/**
 * Years action class
 * 
 * @author Austin Delamar
 * @date 4/20/2017
 */
public class YearsAction implements Handler<RoutingContext> {

    private static Logger logger = LoggerFactory.getLogger(YearsAction.class);
    private List<Year> years = null;

    /**
     * Returns list of all years.
     */
    @Override
    public void handle(RoutingContext context) {

        // /year
        try {
            // gather years
            years = ArchiveInterceptor.getArchiveYears();

            if (years == null || years.isEmpty()) {
                years = null;
                throw new NoDocumentException("No years found");
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

    public List<Year> getYears() {
        return years;
    }

    public void setYears(List<Year> years) {
        this.years = years;
    }
}
