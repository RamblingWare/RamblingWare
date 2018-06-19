package org.oddox.action;

import java.util.List;

import org.oddox.OddoxVerticle;
import org.oddox.action.interceptor.ArchiveInterceptor;
import org.oddox.objects.Year;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cloudant.client.org.lightcouch.NoDocumentException;

import io.vertx.core.Handler;
import io.vertx.reactivex.ext.web.RoutingContext;
import io.vertx.reactivex.ext.web.templ.FreeMarkerTemplateEngine;
import io.vertx.reactivex.ext.web.templ.TemplateEngine;

/**
 * Years action class
 * 
 * @author amdelamar
 * @date 4/20/2017
 */
public class YearsAction implements Handler<RoutingContext> {

    private final static Logger logger = LoggerFactory.getLogger(YearsAction.class);
    private final static TemplateEngine ENGINE = FreeMarkerTemplateEngine.create();
    private List<Year> years = null;

    /**
     * Returns list of all years.
     */
    @Override
    public void handle(RoutingContext context) {
        
        // Don't handle if response ended
        if(context.response().ended()) {
            context.next();
            return;
        }

        // /year
        String templateFile = "year/years.ftl";
        try {
            // gather years
            years = ArchiveInterceptor.getArchiveYears();

            if (years == null || years.isEmpty()) {
                years = null;
                throw new NoDocumentException("No years found");
            }

        } catch (NoDocumentException nfe) {
            templateFile = "year/years.ftl";
        } catch (Exception e) {
            logger.error("Error: " + e.getClass()
                    .getName() + ". Please try again later.", e);
            templateFile = "/error/error.ftl";
        }

        // Bind Context
        context.put("years", years);

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

    public List<Year> getYears() {
        return years;
    }

    public void setYears(List<Year> years) {
        this.years = years;
    }
}
