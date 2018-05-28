package org.oddox.action.api;

import java.util.Map;

import org.oddox.config.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.ext.web.RoutingContext;

/**
 * Health action class
 * 
 * @author Austin Delamar
 * @date 5/14/2018
 */
public class HealthAction implements Handler<RoutingContext> {

    private static Logger logger = LoggerFactory.getLogger(HealthAction.class);

    // JSON response
    private String error;
    private String message;
    private Map<String, String> status;

    /**
     * Returns app status information.
     */
    @Override
    public void handle(RoutingContext context) {

        JsonObject json = new JsonObject();
        try {
            json.put("app", Application.getString("name") != null ? "ok" : "bad");
            json.put("db", Application.getDatabaseService()
                    .getInfo() != null ? "ok" : "bad");

        } catch (Exception e) {
            logger.warn("Health: "+json.encode());
            json.put("error", true);
            json.put("message", e.getMessage());
        }

        // return response
        context.response()
                .putHeader("content-type", "application/json; charset=UTF-8")
                .end(json.encode());
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, String> getStatus() {
        return status;
    }

    public void setStatus(Map<String, String> status) {
        this.status = status;
    }

}
