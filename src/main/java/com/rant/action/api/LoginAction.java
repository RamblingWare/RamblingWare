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
                    int maxAttempts = Application.getInt("default.attempts.limit");
                    // no user found
                    System.out.println("User failed to login. Invalid Username was entered "
                            + username + " (" + attempts + " of " + maxAttempts + ") ("
                            + servletRequest.getRemoteAddr() + ")");
                    throw new Exception("Invalid username or password. (" + attempts + " of "
                            + maxAttempts + ")");
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
        if (code != null && code.isEmpty()) {
            throw new Exception("Invalid or missing code.");
        } else if (code != null && code.length() > 6) {
            throw new Exception("Code cannot be greater than 6 digits.");
        } else if (username == null || username.isEmpty()) {
            throw new Exception("Invalid or missing username.");
        } else if (password == null || password.isEmpty()) {
            throw new Exception("Invalid or missing password.");
        } else if (password.length() > 100) {
            throw new Exception("Password cannot be greater than 100 characters.");
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
        
        int maxAttempts = Application.getInt("default.attempts.limit");
        int lockout =  Application.getInt("default.lockout");

        // lockout for 30 min, if they failed 3 times
        if (attempts >= maxAttempts) {

            if (System.currentTimeMillis() >= (lastAttempt + (lockout * 60 * 1000))) {
                // its been 30mins or more, so unlock
                sessionAttributes.remove("attempts");
                sessionAttributes.remove("lastAttempt");
                System.err.println("Unknown user has waited " + lockout + " min, proceed. ("
                        + servletRequest.getRemoteAddr() + ")(" + servletRequest.getRemoteHost()
                        + ")");
                return false;
            } else {
                // they have already been locked out
                System.err.println("Unknown user has been locked out for " + lockout
                        + " min. (" + servletRequest.getRemoteAddr() + ")("
                        + servletRequest.getRemoteHost() + ") ");
                throw new Exception("You have been locked out for the next " + lockout
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
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = Utils.removeBadChars(code);
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