package org.oddox.action.api;

import java.util.HashMap;
import java.util.Map;

import org.oddox.action.BlogAction;
import org.oddox.config.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.Handler;
import io.vertx.reactivex.ext.web.RoutingContext;

/**
 * Root action class
 * 
 * @author Austin Delamar
 * @date 9/24/2017
 */
public class RootAction implements Handler<RoutingContext> {

    private static Logger logger = LoggerFactory.getLogger(RootAction.class);

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

        try {
            oddox = "Welcome";
            version = Application.getString("version");
            data = new HashMap<String, String>();
            data.put("name", Application.getString("name"));

        } catch (Exception e) {
            error = "error";
            message = e.getMessage();
        }

        // return response
        return NONE;
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
