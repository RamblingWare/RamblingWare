package com.rant.action.manage;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.amdelamar.jhash.Hash;
import com.amdelamar.jotp.OTP;
import com.amdelamar.jotp.type.Type;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.rant.config.Application;
import com.rant.config.Utils;
import com.rant.model.User;
import com.rant.model.UserAware;

/**
 * Login action class
 * 
 * @author Austin Delamar
 * @date 11/9/2015
 */
public class LoginAction extends ActionSupport
        implements
            UserAware,
            ServletResponseAware,
            ServletRequestAware {

    private static final long serialVersionUID = 1L;
    private User user;
    private Map<String, Object> sessionAttributes = null;
    private String username;
    private String password;
    private String code;

    private int lockoutPeriod = 30; // minutes
    private int maxAttempts = 3;
    private int attempts = 0;
    private long lastAttempt = 0;

    @Override
    public String execute() {

        // are they already logged in?
        sessionAttributes = ActionContext.getContext().getSession();
        if (sessionAttributes.get("login") != null) {
            // logged in already.
            addActionMessage("You are already logged in.");
            return SUCCESS;
        }

        // now try to see if they can login
        if (username != null && password != null) {
            // logging in with password
            return passwordLogin();
        } else if (code != null) {
            // logging in with OTP
            return otpLogin();
        } else {
            // they opened the form
            return ERROR;
        }
    }

    /**
     * Check if they can login with the password entered.
     * 
     * @return SUCCESS if true, INPUT if wrong code, or ERROR if error occurred
     */
    private String passwordLogin() {

        // check if locked out
        if (isLockedOut()) {
            return Action.ERROR;
        }

        try {
            user = Application.getDatabaseSource().getUser(username);

            if (user != null && Hash.verify(password, user.getPassword())) {
                // password matches!
                // Login success

                if (user.isOTPEnabled()) {
                    // user needs to enter a OTP still before they are
                    // logged in.
                    sessionAttributes.remove("attempts");
                    sessionAttributes.remove("lastAttempt");
                    sessionAttributes = ActionContext.getContext().getSession();
                    sessionAttributes.put("USER", user);

                    System.out.println("User logged in " + user.getUsername()
                            + " and now has to enter their OTP (" + servletRequest.getRemoteAddr()
                            + ")");

                    return INPUT;
                } else {
                    // user didn't enable OTP / 2FA yet.

                    // update user's last login date
                    // TODO Application.getDatabaseSource().loginUser(user);

                    sessionAttributes.remove("attempts");
                    sessionAttributes.remove("lastAttempt");
                    sessionAttributes = ActionContext.getContext().getSession();
                    sessionAttributes.put("login", "true");
                    sessionAttributes.put("context", new Date());
                    sessionAttributes.put("USER", user);

                    if (user.getLastLoginDate() != null) {
                        addActionMessage("Welcome back, " + user.getName() + ". Last login was on "
                                + Utils.formatReadableDate(user.getLastLoginDate()));
                    } else {
                        addActionMessage("Welcome, " + user.getName() + ".");
                    }
                    System.out.println("User logged in: " + user.getUsername() + " ("
                            + servletRequest.getRemoteAddr() + ")");

                    return SUCCESS;
                }

            } else {
                // no user found
                addActionError(
                        "Invalid username or password. (" + attempts + "/" + maxAttempts + ")");
                System.out.println("User failed to login. Invalid Username was entered " + username
                        + " (" + attempts + "/" + maxAttempts + ") ("
                        + servletRequest.getRemoteAddr() + ")");
                return ERROR;
            }
        } catch (Exception e) {
            addActionError(e.getMessage());
            e.printStackTrace();
            return ERROR;
        }
    }

    /**
     * Check if they can login with the OTP code entered.
     * 
     * @return SUCCESS if true, INPUT if wrong code, or ERROR if error occurred
     */
    private String otpLogin() {

        // check if locked out
        if (isLockedOut()) {
            return Action.ERROR;
        }

        // OTP / 2FA code provided.
        // verify if it is correct

        sessionAttributes = ActionContext.getContext().getSession();
        user = (User) sessionAttributes.get("USER");

        boolean validCode = false;
        try {
            validCode = OTP.verify(user.getKeySecret(), OTP.timeInHex(), code, 6, Type.TOTP);
        } catch (Exception e) {
            System.err.println("Error when validating OTP: " + e.getMessage());
        }

        if (validCode) {
            user.setOTPAuthenticated(true);
            sessionAttributes.put("login", "true");
            sessionAttributes.put("context", new Date());
            sessionAttributes.put("USER", user);
            sessionAttributes.remove("attempts");
            sessionAttributes.remove("lastAttempt");

            try {
                // update user's last login date
                // TODO Application.getDatabaseSource().loginUser(user);

                addActionMessage("Welcome back, " + user.getName() + ". Last login was on "
                        + Utils.formatReadableDate(user.getLastLoginDate()));
                System.out.println("User logged in: " + user.getUsername() + " with their OTP ("
                        + servletRequest.getRemoteAddr() + ")");

            } catch (Exception e) {
                addActionError(e.getMessage());
                e.printStackTrace();
                return ERROR;
            }
            return SUCCESS;

        } else {
            // OTP code did not match!
            addActionError("Invalid code. (" + attempts + "/" + maxAttempts + ")");
            System.out.println("User tried to login with OTP: " + user.getUsername() + " ("
                    + attempts + "/" + maxAttempts + ") (" + servletRequest.getRemoteAddr() + ")");

            return INPUT;
        }

    }

    /**
     * Check if the user has attempted too many times, and lock them out if they have.
     * 
     * @return true if locked out
     */
    private boolean isLockedOut() {

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
        if (attempts >= maxAttempts) {

            if (System.currentTimeMillis() >= (lastAttempt + (lockoutPeriod * 60 * 1000))) {
                // its been 30mins or more, so unlock
                sessionAttributes.remove("attempts");
                sessionAttributes.remove("lastAttempt");
                System.err.println("Unknown user has waited " + lockoutPeriod + " min, proceed. ("
                        + servletRequest.getRemoteAddr() + ")(" + servletRequest.getRemoteHost()
                        + ")");
                return false;
            } else {
                // they have already been locked out
                System.err.println("Unknown user has been locked out for " + lockoutPeriod
                        + " min. (" + servletRequest.getRemoteAddr() + ")("
                        + servletRequest.getRemoteHost() + ") ");
                addActionError("You have been locked out for the next " + lockoutPeriod
                        + " minutes, for too many attempts.");
                return true;
            }
        }
        return false;
    }

    public void setSession(Map<String, Object> sessionAttributes) {
        this.sessionAttributes = sessionAttributes;
    }

    @Override
    public void setUser(User user) {
        this.user = user;
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

}