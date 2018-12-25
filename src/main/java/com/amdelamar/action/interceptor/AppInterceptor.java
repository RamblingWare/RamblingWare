package com.amdelamar.action.interceptor;

import com.amdelamar.config.Application;
import com.amdelamar.config.Utils;

import io.vertx.core.Handler;
import io.vertx.reactivex.ext.web.RoutingContext;

/**
 * Application Interceptor class
 * 
 * @author amdelamar
 * @date 12/17/2017
 */
public class AppInterceptor implements Handler<RoutingContext> {

    @Override
    public void handle(RoutingContext context) {

        // set app properties
        context.put("date", Utils.getDateRfc1123());
        Application.getAppConfig().getSettings().forEach((k, v) -> {
            context.put(k, v);
        });

        context.next();
    }
}
