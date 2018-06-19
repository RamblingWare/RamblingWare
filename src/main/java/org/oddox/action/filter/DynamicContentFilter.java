package org.oddox.action.filter;

import org.oddox.config.AppHeaders;
import org.oddox.config.Application;
import org.oddox.objects.Header;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.Handler;
import io.vertx.reactivex.ext.web.RoutingContext;

/**
 * DynamicContentFilter class modifies HTTP Headers before sending out a response from a template or action.
 * 
 * @author amdelamar
 * @date 7/03/2017
 */
public class DynamicContentFilter implements Handler<RoutingContext> {

    private final static Logger logger = LoggerFactory.getLogger(DynamicContentFilter.class);
    
    private static final long EXPIRETIME = 86400000l;
    private static long cacheTime = 0l;

    @Override
    public void handle(RoutingContext context) {

        // Has it been 24 hours since fresh archive check?
        long diff = Math.abs(System.currentTimeMillis() - cacheTime);
        if (diff >= EXPIRETIME) {
            // cache expired.
            // get fresh app headers
            logger.trace("New cache for AppHeaders");

            // get the http headers from db
            AppHeaders appHeaders = Application.getDatabaseService()
                    .getAppHeaders();
            Application.setAppHeaders(appHeaders);

            // set new cacheTime
            cacheTime = System.currentTimeMillis();
        }
        // else,
        // just use cache http headers,
        // which at this point is already set.

        // apply all headers to our response
        if (Application.getAppHeaders() != null && Application.getAppHeaders()
                .getHeaders() != null) {
            for (Header header : Application.getAppHeaders()
                    .getHeaders()) {
                context.response()
                        .putHeader(header.getKey(), header.getValue());
            }
        }

        context.next();
    }

}
