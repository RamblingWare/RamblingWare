package org.oddox.action.interceptor;

import org.oddox.config.Application;
import org.oddox.config.Utils;

import io.vertx.core.Handler;
import io.vertx.reactivex.ext.web.RoutingContext;

/**
 * Application Interceptor class
 * 
 * @author Austin Delamar
 * @date 12/17/2017
 */
public class AppInterceptor implements Handler<RoutingContext> {

    @Override
    public void handle(RoutingContext context) {

        // set app properties
        context.put("date", Utils.getDateRfc1123());
        Application.getAppConfig().getSettings().forEach((k,v) -> {
            context.put(k, v);
        });

        context.next();
    }
}
