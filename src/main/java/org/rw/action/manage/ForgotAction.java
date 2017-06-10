package org.rw.action.manage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.rw.config.Utils;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Forgot action class
 * 
 * @author Austin Delamar
 * @date 12/19/2016
 */
public class ForgotAction extends ActionSupport
        implements
            ServletResponseAware,
            ServletRequestAware {

    private static final long serialVersionUID = 1L;

    private String email; // need email to send reminders or resets
    private String code;
    private String type; // 'username' or 'password' or 'twofactor'

    private boolean remind; // true if they forgot username
    private boolean reset; // true if they forgot password
    private boolean recover; // true if they forgot two factor

    private int lockoutPeriod = 30; // minutes
    private int maxAttempts = 3;
    private static int attempts = 0;
    private static long lastAttempt = 0;

    @Override
    public String execute() {

        // now try to see if they can remember their email
        if (remind || reset) {
            // check if locked out
            if (isLockedOut()) {
                return Action.ERROR;
            }

            if (email != null && Utils.isValidEmail(email)) {

                // TODO
                // if email is good, and remind, then send reminder email

                // if email is good, and reset, then send email link to reset
                // password
                // generate a unique token that can only be used once, without
                // login.

                if (remind) {
                    addActionMessage(
                            "You have been sent a reminder. Please check your inbox for your Username.");
                    System.out.println("User has been sent a reminder email.");
                } else if (reset) {
                    addActionMessage(
                            "You have been sent a reset token. Please check your inbox to reset your password.");
                    System.out.println("User has been sent a reset email.");
                }

                attempts = 0;
                return SUCCESS;
            } else {
                // no user found
                addActionMessage("Invalid Email Address. (" + attempts + "/" + maxAttempts + ")");
                System.out.println("User failed to recover. Invalid Email was entered " + email
                        + " (" + attempts + "/" + maxAttempts + ") ("
                        + servletRequest.getRemoteAddr() + ")");
            }
        } else if (recover) {

            // check if locked out
            if (isLockedOut()) {
                return Action.ERROR;
            }

            if (code != null && code.equalsIgnoreCase("123456")) {
                // TODO compare recovery codes

                addActionMessage(
                        "You have been sent a recovery link. Please check your inbox to recover your account.");
                System.out.println("User has been sent a recovery email.");

                attempts = 0;
                return SUCCESS;
            } else {
                // invalid recovery code
                addActionMessage("Invalid Recovery Code. (" + attempts + "/" + maxAttempts + ")");
                System.out
                        .println("User tried to recover with an invalid Recovery Code. (" + attempts
                                + "/" + maxAttempts + ") (" + servletRequest.getRemoteAddr() + ")");
                return ERROR;
            }
        }

        return NONE;
    }

    /**
     * Check if the user has attempted too many times, and lock them out if they have.
     * 
     * @return true if locked out
     */
    private boolean isLockedOut() {
        try {
            // wait a bit, just to slow this request type down...
            attempts++;
            Thread.sleep(500 * attempts);
        } catch (InterruptedException e1) {
            /* Don't bother to catch this exception */
        }

        // lockout for 30 min, if they failed 3 times
        if (attempts >= maxAttempts) {
            if (lastAttempt == 0) {
                // this is their 5th try, so record their time
                lastAttempt = System.currentTimeMillis();
                System.err.println("Unknown user has been locked out for " + lockoutPeriod
                        + " min. (" + servletRequest.getRemoteAddr() + ")("
                        + servletRequest.getRemoteHost() + ")");
                addActionError("You have been locked out for the next " + lockoutPeriod
                        + " minutes, for too many attempts.");
                return true;
            } else if (System.currentTimeMillis() >= (lastAttempt + (lockoutPeriod * 60 * 1000))) {
                // its been 30mins or more, so unlock
                attempts = 0;
                lastAttempt = 0;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = Utils.removeBadChars(email);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = Utils.removeBadChars(code);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        if (type == null)
            type = "username";
        this.type = Utils.removeBadChars(type);
    }

    public int getAttempts() {
        return attempts;
    }

    public boolean isRemind() {
        return remind;
    }

    public void setRemind(boolean remind) {
        this.remind = remind;
    }

    public boolean isReset() {
        return reset;
    }

    public void setReset(boolean reset) {
        this.reset = reset;
    }

    public boolean isRecover() {
        return recover;
    }

    public void setRecover(boolean recover) {
        this.recover = recover;
    }

}