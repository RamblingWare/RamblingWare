package org.oddox.action.interceptor;

import java.util.Calendar;
import java.util.Date;

import org.oddox.config.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.Handler;
import io.vertx.reactivex.core.http.HttpServerRequest;
import io.vertx.reactivex.core.http.HttpServerResponse;
import io.vertx.reactivex.ext.web.RoutingContext;

/**
 * StaticContentFilter class modifies HTTP Headers before sending out a response from a static file.
 * 
 * @author amdelamar
 * @date 7/03/2017
 */
public class StaticContentInterceptor implements Handler<RoutingContext> {
    
    private final static Logger logger = LoggerFactory.getLogger(StaticContentInterceptor.class);

    @Override
    public void handle(RoutingContext context) {

        HttpServerRequest request = context.request();
        HttpServerResponse response = context.response();

        // Return a 304 "Not Modified" if the file hasn't been updated.
        long ifModifiedSince = 0;
        try {
            // only do this if browser asks "If-Modified-Since"
            ifModifiedSince = Long.parseLong(request.getHeader("If-Modified-Since"));
        } catch (Exception e) {
            logger.warn("Invalid If-Modified-Since header value: '" + request.getHeader("If-Modified-Since")
                    + "', ignoring");
        }

        if (request.uri()
                .endsWith(".ico")) {
            // favicon content-type
            response.putHeader("Content-Type", "image/x-icon");
        }

        long now = System.currentTimeMillis();
        long lastModifiedMillis = now;

        // Replace information that might reveal too much to help potential attackers to exploit the
        // server. Alternatively, you could put bogus info here, like .NET or other irrelevant tech.
        //response.setHeader("X-Powered-By", "");
        //response.setHeader("Server", "");

        // 1 month seems to be the minimum recommended period for static resources acc to
        // https://developers.google.com/speed/docs/best-practices/caching#LeverageBrowserCaching
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(now);
        cal.add(Calendar.MONTH, 1);

        if (ifModifiedSince > 0 && ifModifiedSince <= lastModifiedMillis) {
            // 304 "Not Modified" content is not sent
            response.setStatusCode(304);
        }

        // set heading information for caching static content
        response.putHeader("Date", Utils.formatRfc1123Date(new Date(now)));
        response.putHeader("Expires", Utils.formatRfc1123Date(new Date(cal.getTimeInMillis())));
        response.putHeader("Retry-After", Utils.formatRfc1123Date(new Date(cal.getTimeInMillis())));
        response.putHeader("Cache-Control", "max-age=2628000, public");
        response.putHeader("Last-Modified", Utils.formatRfc1123Date(new Date(lastModifiedMillis)));

        context.next();
    }

}
