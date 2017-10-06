package com.rant.action.api;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.rant.config.Application;
import com.rant.config.Utils;

/**
 * Login action class
 * 
 * @author Austin Delamar
 * @date 11/9/2015
 */
public class LoginAction extends ActionSupport
        implements
            ServletResponseAware,
            ServletRequestAware {

    private static final long serialVersionUID = 1L;
    private Map<String, Object> sessionAttributes = null;
    private String username;
    private String password;
    private String code;

    protected static final int LOCKOUT_MINS = 30; // minutes
    protected static final int MAX_ATTEMPTS = 3;
    private int attempts = 0;
    private long lastAttempt = 0;

    // JSON response
    private String login;
    private String error;
    private String message;
    private Map<String, String> data;

    @SuppressWarnings("unchecked")
    @Override
    public String execute() {

        try {
            sessionAttributes = ActionContext.getContext().getSession();
            // are they already logged in?
            if (sessionAttributes.get("login") != null) {
                login = "ok";
                data = (Map<String, String>) sessionAttributes.get("data");
                message = "Welcome back, " + data.get("username") + ".";
                data.put("token", UUID.randomUUID().toString());
                data.put("time", Utils.getDateIso8601());
            }

            // check if locked out
            // check inputs
            if (login == null && !isLockedOut() && validParameters()) {

                // try login
                if (Application.getDatabaseService().loginUser(username, password)) {
                    // Login success

                    login = "ok";
                    message = "Welcome, " + username + ".";
                    data = new HashMap<String, String>();
                    data.put("url", Application.getDatabaseService().getDatabase().getUrl());
                    data.put("username", username);
                    data.put("token", UUID.randomUUID().toString());
                    data.put("time", Utils.getDateIso8601());

                    sessionAttributes.remove("attempts");
                    sessionAttributes.remove("lastAttempt");
                    sessionAttributes.put("login", "true");
                    sessionAttributes.put("data", data);
                    addActionMessage("Welcome, " + username + ".");
                    System.out.println("User logged in: " + username + " ("
                            + servletRequest.getRemoteAddr() + ")");

                } else {
                    // no user found
                    System.out.println("User failed to login. Invalid Username was entered "
                            + username + " (" + attempts + " of " + MAX_ATTEMPTS + ") ("
                            + servletRequest.getRemoteAddr() + ")");
                    throw new Exception("Invalid username or password. (" + attempts + " of "
                            + MAX_ATTEMPTS + ")");
                }
            }
        } catch (Exception e) {
            error = "error";
            message = e.getMessage();
        }

        // return response
        System.out.println(message);
        return NONE;
    }

    /**
     * Check username and password parameters.
     * 
     * @return true if ok
     * @throws Exception
     *             if invalid
     */
    protected boolean validParameters() throws Exception {
        if (username == null || username.isEmpty()) {
            throw new Exception("Invalid or missing username.");
        } else if (password == null || password.isEmpty()) {
            throw new Exception("Invalid or missing password.");
        } else {
            return true;
        }
    }

    /**
     * Check if the user has attempted too many times, and lock them out if they have.
     * 
     * @return true if locked out
     * @throws Exception
     *             if locked out
     */
    protected boolean isLockedOut() throws Exception {
        // count login attempts
        // and remember when their last attempt was
        if (sessionAttributes.get("attempts") == null) {
            attempts = 1;
        } else {
            attempts = (int) sessionAttributes.get("attempts");
            attempts++;
        }
        if (sessionAttributes.get("lastAttempt") == null) {
            lastAttempt = System.currentTimeMillis();
        } else {
            lastAttempt = (long) sessionAttributes.get("lastAttempt");
        }
        sessionAttributes.put("attempts", attempts);
        sessionAttributes.put("lastAttempt", lastAttempt);

        // lockout for 30 min, if they failed 3 times
        if (attempts >= MAX_ATTEMPTS) {

            if (System.currentTimeMillis() >= (lastAttempt + (LOCKOUT_MINS * 60 * 1000))) {
                // its been 30mins or more, so unlock
                sessionAttributes.remove("attempts");
                sessionAttributes.remove("lastAttempt");
                System.err.println("Unknown user has waited " + LOCKOUT_MINS + " min, proceed. ("
                        + servletRequest.getRemoteAddr() + ")(" + servletRequest.getRemoteHost()
                        + ")");
                return false;
            } else {
                // they have already been locked out
                System.err.println("Unknown user has been locked out for " + LOCKOUT_MINS
                        + " min. (" + servletRequest.getRemoteAddr() + ")("
                        + servletRequest.getRemoteHost() + ") ");
                throw new Exception("You have been locked out for the next " + LOCKOUT_MINS
                        + " minutes, for too many attempts.");
            }
        }
        return false;
    }

    public void setSession(Map<String, Object> sessionAttributes) {
        this.sessionAttributes = sessionAttributes;
    }

    protected HttpServletResponse servletResponse;

    @Override
    public void setServletResponse(HttpServletResponse servletResponse) {
        this.servletResponse = servletResponse;
    }

    protected HttpServletRequest servletRequest;

    @Override
    public void setServletRequest(HttpServletRequest servletRequest) {
        this.servletRequest = servletRequest;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = Utils.removeBadChars(username);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if(password != null && password.length() > 100) {
            this.password = password.substring(0, 100); 
        } else {
            this.password = password;    
        }
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        if(code != null && code.length() > 6) {
            this.code =  Utils.removeBadChars(code.substring(0, 6));
        } else {
            this.code = Utils.removeBadChars(code);
        }
    }

    public int getAttempts() {
        return attempts;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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