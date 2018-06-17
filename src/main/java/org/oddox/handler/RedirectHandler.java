package org.oddox.handler;

import org.oddox.OddoxVerticle;

import io.vertx.core.Handler;
import io.vertx.reactivex.core.http.HttpServerRequest;

/**
 * Redirects HTTP calls to HTTPS with a 302 (Found) code and "Location" Header.
 * 
 * @author amdelamar
 * @date 05/28/2018
 */
public class RedirectHandler implements Handler<HttpServerRequest> {

    @Override
    public void handle(HttpServerRequest request) {

        // replace with https
        String url = request.absoluteURI();
        url = "https" + url.substring(4);

        // change port if needed
        if (url.contains(Integer.toString(OddoxVerticle.PORT))) {
            url = url.replaceFirst(Integer.toString(OddoxVerticle.PORT), Integer.toString(OddoxVerticle.HTTPS_PORT));
        }

        request.response()
                .setStatusCode(302)
                .putHeader("Location", url)
                .end();
    }

}
