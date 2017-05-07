package org.rw.action.manage;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.amdelamar.jhash.Hash;
import org.amdelamar.jotp.OTP;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.rw.action.model.Author;
import org.rw.action.model.UserAware;
import org.rw.config.Application;
import org.rw.config.Utils;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Login action class
 * 
 * @author Austin Delamar
 * @date 11/9/2015
 */
public class LoginAction extends ActionSupport implements UserAware, ServletResponseAware, ServletRequestAware {

	private static final long serialVersionUID = 1L;
	private Author user;
	private Map<String, Object> sessionAttributes = null;
	private String username;
	private String password;
	private String code;

	private int lockoutPeriod = 30; // minutes
	private int maxAttempts = 3;
	private static int attempts = 0;
	private static long lastAttempt = 0;

	@Override
	public String execute() {

		// check if locked out
		if (isLockedOut()) {
			return Action.ERROR;
		}

		// now try to see if they can login
		if (username != null && password != null) {
			try {
				user = Application.getDatabaseSource().getUser(username);

				if (user != null && Hash.verify(password, user.getPassword())) {
					// password matches!
					// Login success

					if (user.isOTPEnabled()) {
						// user needs to enter a OTP still before they are
						// logged in.
						attempts = 0;
						lastAttempt = 0;
						sessionAttributes = ActionContext.getContext().getSession();
						sessionAttributes.put("USER", user);

						System.out.println("User logged in " + user.getUsername() + " and now has to enter their OTP ("
								+ servletRequest.getRemoteAddr() + ")");

						return INPUT;
					} else {
						// user didn't enable OTP / 2FA yet.

						// update user's last login date
						Application.getDatabaseSource().loginUser(user);

						attempts = 0;
						lastAttempt = 0;
						sessionAttributes = ActionContext.getContext().getSession();
						sessionAttributes.put("login", "true");
						sessionAttributes.put("context", new Date());
						sessionAttributes.put("USER", user);

						addActionMessage("Welcome back, " + user.getName() + ". Last login was on "
								+ Utils.formatReadableDate(user.getLastLoginDate()));
						System.out.println(
								"User logged in: " + user.getUsername() + " (" + servletRequest.getRemoteAddr() + ")");

						return SUCCESS;
					}

				} else {
					// no user found
					addActionError("Invalid username or password. (" + attempts + "/" + maxAttempts + ")");
					System.out.println("User failed to login. Invalid Username was entered " + username + " ("
							+ attempts + "/" + maxAttempts + ") (" + servletRequest.getRemoteAddr() + ")");
				}
			} catch (Exception e) {
				addActionError(e.getMessage());
				e.printStackTrace();
			}
		} else if (code != null) {
			// OTP / 2FA code provided.
			// verify if it is correct

			sessionAttributes = ActionContext.getContext().getSession();
			user = (Author) sessionAttributes.get("USER");

			if (OTP.verifyTotp(user.getKeySecret(), code, 6)) {
				user.setOTPAuthenticated(true);
				sessionAttributes.put("login", "true");
				sessionAttributes.put("context", new Date());
				sessionAttributes.put("USER", user);
				attempts = 0;
				lastAttempt = 0;

				try {
					// update user's last login date
					Application.getDatabaseSource().loginUser(user);

					addActionMessage("Welcome back, " + user.getName() + ". Last login was on "
							+ Utils.formatReadableDate(user.getLastLoginDate()));
					System.out.println("User logged in: " + user.getUsername() + " with their OTP ("
							+ servletRequest.getRemoteAddr() + ")");

				} catch (Exception e) {
					addActionError(e.getMessage());
					e.printStackTrace();
				}
				return SUCCESS;

			} else {
				// OTP code did not match!
				addActionError("Invalid code. (" + attempts + "/" + maxAttempts + ")");
				System.out.println("User tried to login with OTP: " + user.getUsername() + " (" + attempts + "/"
						+ maxAttempts + ") (" + servletRequest.getRemoteAddr() + ")");

				return INPUT;
			}
		} else {
			// invalid login
			addActionError("Invalid code. (" + attempts + "/" + maxAttempts + ")");
			System.out.println("User tried to login with an invalid Username. (" + attempts + "/" + maxAttempts + ") ("
					+ servletRequest.getRemoteAddr() + ")");
		}
		return ERROR;
	}

	/**
	 * Check if the user has attempted too many times, and lock them out if they
	 * have.
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
				System.err.println("Unknown user has been locked out for " + lockoutPeriod + " min. ("
						+ servletRequest.getRemoteAddr() + ")(" + servletRequest.getRemoteHost() + ")");
				addActionError(
						"You have been locked out for the next " + lockoutPeriod + " minutes, for too many attempts.");
				return true;
			} else if (System.currentTimeMillis() >= (lastAttempt + (lockoutPeriod * 60 * 1000))) {
				// its been 30mins or more, so unlock
				attempts = 0;
				lastAttempt = 0;
				System.err.println("Unknown user has waited " + lockoutPeriod + " min, proceed. ("
						+ servletRequest.getRemoteAddr() + ")(" + servletRequest.getRemoteHost() + ")");
				return false;
			} else {
				// they have already been locked out
				System.err.println("Unknown user has been locked out for " + lockoutPeriod + " min. ("
						+ servletRequest.getRemoteAddr() + ")(" + servletRequest.getRemoteHost() + ") ");
				addActionError(
						"You have been locked out for the next " + lockoutPeriod + " minutes, for too many attempts.");
				return true;
			}
		}
		return false;
	}

	public void setSession(Map<String, Object> sessionAttributes) {
		this.sessionAttributes = sessionAttributes;
	}

	@Override
	public void setUser(Author user) {
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