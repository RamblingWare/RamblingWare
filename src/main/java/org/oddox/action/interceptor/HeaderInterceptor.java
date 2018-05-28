package org.oddox.action.interceptor;

import io.vertx.core.Handler;
import io.vertx.reactivex.ext.web.RoutingContext;

/**
 * HeaderInterceptor class appends the HTTP Headers before receving a request. Remember the original
 * URI request, because if we forward, chain, redirect at all then this value is lost. Its
 * beneficial to keep for pagination and such.
 * 
 * @author Austin Delamar
 * @date 6/11/2017
 */
public class HeaderInterceptor implements Handler<RoutingContext> {

    @Override
    public void handle(RoutingContext context) {

        // Remember the original URI request, because if we forward, chain, redirect at all
        // then this value is lost. Its beneficial to keep for pagination and such.
        String pageUri = context.request().absoluteURI();

        // Valid URIs might have different contexts...
        // /blog/page/1
        // /year/2017/page/1
        // /category/test/page/1
        // /tag/java/page/1
        // /author/page/1

        if (pageUri.contains("/page/")) {
            pageUri = pageUri.substring(0, pageUri.indexOf("/page/")) + "/page";
        } else {
            pageUri = pageUri + "/page";
        }

        // remove any duplicate slashes
        while (pageUri.contains("//")) {
            pageUri = pageUri.replace("//", "/");
        }
        
        context.put("pageUri", pageUri);
        context.next();
    }

}
