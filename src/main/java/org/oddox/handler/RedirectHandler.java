package org.oddox.handler;

import org.oddox.MainVerticle;

import io.vertx.core.Handler;
import io.vertx.reactivex.core.http.HttpServerRequest;

/**
 * Redirects HTTP calls to HTTPS with a 302 (Found) code and "Location" Header.
 * @author amdelamar
 */
public class RedirectHandler implements Handler<HttpServerRequest> {

    @Override
    public void handle(HttpServerRequest request) {

        // replace with https
        String url = request.absoluteURI();
        url = "https" + url.substring(4);

        // change port if needed
        if (url.contains(Integer.toString(MainVerticle.HTTP_PORT))) {
            url = url.replaceFirst(Integer.toString(MainVerticle.HTTP_PORT), Integer.toString(MainVerticle.HTTPS_PORT));
        }

        request.response()
                .setStatusCode(302)
                .putHeader("Location", url)
                .end();
    }

}
