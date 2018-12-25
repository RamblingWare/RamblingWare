package com.amdelamar.action.api;

import java.util.Map;

import com.amdelamar.config.Application;
import com.amdelamar.OddoxVerticle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.ext.web.RoutingContext;

/**
 * Root action class
 * 
 * @author amdelamar
 * @date 9/24/2017
 */
public class RootAction implements Handler<RoutingContext> {

    private final static Logger logger = LoggerFactory.getLogger(RootAction.class);

    // JSON response
    private String oddox;
    private String version;
    private String error;
    private String message;
    private Map<String, String> data;

    /**
     * Returns application information.
     */
    @Override
    public void handle(RoutingContext context) {
        
        // Don't handle if response ended
        if(context.response().ended()) {
            context.next();
            return;
        }

        JsonObject json = new JsonObject();
        try {
            json.put("oddox", "Welcome");
            json.put("version", OddoxVerticle.VERSION);
            json.put("name", Application.getString("name"));

        } catch (Exception e) {
            logger.warn("Root: "+json.encode());
            json.put("error", true);
            json.put("message", e.getMessage());
        }

        // return response
        context.response()
        .putHeader("content-type", "application/json; charset=UTF-8")
        .end(json.encode());
    }

    public String getOddox() {
        return oddox;
    }

    public void setOddox(String oddox) {
        this.oddox = oddox;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
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

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }

}
