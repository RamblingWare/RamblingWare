package org.rw.action.manage;

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

import com.opensymphony.xwork2.ActionSupport;

/**
 * Settings action class
 * 
 * @author Austin Delamar
 * @date 12/19/2016
 */
public class SettingsAction extends ActionSupport implements UserAware, ServletResponseAware, ServletRequestAware {

	private static final long serialVersionUID = 1L;
	private Author user;

	private boolean account; // true if they updated their account
	private boolean password; // true if they updated their password
	private boolean security; // true if they updated their security

	private String username;
	private String email;
	private int role; // 0 for author, 1 for admin

	private String passwordOld;
	private String passwordNew;
	private String passwordVerify;

	private String secret; // Base32 160-bit random
	private String code; // 6-digit code

	public String execute() {

		try { /* Try to set UTF-8 page encoding */
			servletRequest.setCharacterEncoding("UTF-8");
		} catch (Exception e) {
			System.err.println("Failed to set UTF-8 request encoding.");
		}

		if (!user.isOTPEnabled() && user.getKeySecret() == null) {
			// generate secret
			secret = OTP.randomBase32(20);
			user.setKeySecret(secret);
		} else {
			// remember the generated secret
			secret = user.getKeySecret();
		}

		if (account) {
			// they've changed their account info

			// validate inputs
			if (!Utils.isValidEmail(email) || email.isEmpty()) {
				addActionError("Email address is not valid. Please try again.");
				return ERROR;
			}
			if (username.isEmpty()) {
				addActionError("Username is not valid. Please try again.");
				return ERROR;
			}

			// update user information
			try {
				Author updatedUser = user;
				updatedUser.setUsername(username);
				updatedUser.setEmail(email);

				if (Application.getDatabaseSource().editUser(updatedUser)) {
					// Success
					// update user settings in session
					user.setUsername(username);
					user.setEmail(email);
					System.out.println("User " + user.getUsername() + " updated their account.");
					addActionMessage("Changes were saved.");
					return SUCCESS;
				} else {
					// failed to update user
					addActionError("Failed to update account. Please try again.");
					System.out.println("Failed to update the user. " + user.getUsername());
					return ERROR;
				}

			} catch (Exception e) {
				addActionError("An error occurred: " + e.getMessage());
				e.printStackTrace();
				return ERROR;
			}
		} else if (password) {
			// they're changing their password

			// validate inputs
			if (passwordOld == null || passwordOld.isEmpty()) {
				addActionError("Current Password is not valid. Please try again.");
				return ERROR;
			}
			if (passwordNew == null || passwordNew.isEmpty()) {
				addActionError("New Password is not valid. Please try again.");
				return ERROR;
			}
			if (passwordVerify == null || passwordVerify.isEmpty()) {
				addActionError("New Password is not valid. Please try again.");
				return ERROR;
			}
			if (passwordNew.length() < 8) {
				addActionError("New Password must be 8 or more characters. Please try again.");
				return ERROR;
			}
			if (!passwordNew.equals(passwordVerify)) {
				addActionError("New Password did not match! Please try again.");
				return ERROR;
			}

			// update user information
			try {
				if (Hash.verify(passwordOld, user.getPassword())) {
					// old password matches, so update with new password

					Author updatedUser = user;

					// salt and hash the password
					updatedUser.setPassword(Hash.create(passwordNew, Hash.PBKDF2_HMACSHA256));

					if (Application.getDatabaseSource().editUser(updatedUser)) {
						// Success
						// update user settings in session
						user.setPassword(updatedUser.getPassword());
						System.out.println("User " + user.getUsername() + " updated their password.");
						addActionMessage("Password was successfully changed.");
						return SUCCESS;
					} else {
						// failed to update user password
						addActionError("Failed to update your password. Please try again.");
						System.out.println("Failed to update the user. " + user.getUsername());
						return ERROR;						
					}
				} else {
					// password does not match
					addActionError("Invalid Password. Please try again.");
					System.out.println(
							"User " + user.getUsername() + " failed to change password. Invalid Password was entered.");
					return ERROR;
				}
			} catch (Exception e) {
				addActionError("Failed to update your password. Please try again.");
				e.printStackTrace();
				return ERROR;
			}
		} else if (security) {
			// they're changing their security

			if (passwordOld != null) {
				// they want to disable 2FA.
				// validate inputs
				if (passwordOld.isEmpty()) {
					addActionError("Password is not valid. Please try again.");
					return ERROR;
				}
				if (!Utils.removeAllSpaces(code)
						.equals(Utils.removeAllSpaces(user.getKeyRecover()))) {
					addActionError("Recovery Code is not valid. Please try again.");
					return ERROR;
				}
				
				// update user information
				try {
					Author updatedUser = user;

					if (Hash.verify(passwordOld, user.getPassword())) {
						// old password matches, so disable 2FA
						updatedUser.setOTPAuthenticated(true);
						updatedUser.setOTPEnabled(false);
						updatedUser.setKeySecret(null);
						updatedUser.setKeyRecover(null);
						
						if (Application.getDatabaseSource().editUser(updatedUser)) {
							// Success
							// update user settings in session
							user.setOTPAuthenticated(true);
							user.setOTPEnabled(false);
							user.setKeySecret(null);
							user.setKeyRecover(null);

							System.out.println("User " + user.getUsername() + " successfully disabled 2FA.");
							addActionMessage("Two Factor Authentication was disabled!");
							return SUCCESS;
						} else {
							// failed to update user security
							addActionError("Failed to disable 2FA. Please try again.");
							System.out.println("Failed to disable 2FA for user " + user.getUsername());
							return ERROR;
						}
					} else {
						// password does not match
						addActionError("Invalid Password. Please try again.");
						System.out.println(
								"User " + user.getUsername() + " failed to disable 2FA. Invalid Password was entered.");
						return ERROR;
					}
				} catch (Exception e) {
					addActionError("Failed to disable 2FA. Please try again.");
					e.printStackTrace();
					return ERROR;
				}

				
			} else if (OTP.verifyTotp(secret, code, 6)) {
				// they want to enable 2FA.
				// generate recovery key
				String recover = OTP.random("ABCDEFGHIJKLMNOPQRSTUVWXYZ234567", 12);
				recover = recover.substring(0, 3) + " " + recover.substring(3, 6) + " " + recover.substring(6, 9) + " "
						+ recover.substring(9);

				// update user information
				try {
					Author updatedUser = user;
					updatedUser.setOTPEnabled(true);
					updatedUser.setKeySecret(secret);
					updatedUser.setKeyRecover(recover);

					// code matches, so enable 2FA
					if (Application.getDatabaseSource().editUser(updatedUser)) {
						// Success
						// update user settings in session
						user.setOTPEnabled(true);
						user.setOTPAuthenticated(false);
						user.setKeySecret(secret);
						user.setKeyRecover(recover);

						System.out.println("User " + user.getUsername() + " successfully enabled 2FA.");
						addActionMessage("Two Factor Authentication was successfully enabled!");
						return SUCCESS;
					} else {
						// failed to update user security
						addActionError("Failed to enable 2FA. Please try again.");
						System.out.println("Failed to enable 2FA for user " + user.getUsername());
						return ERROR;
					}

				} catch (Exception e) {
					addActionError("An error occurred: " + e.getMessage());
					e.printStackTrace();
					return ERROR;
				}
				
			} else {
				System.out.println("User's Security Code did not match the generated code.");
				addActionError("Security Code Invalid. Please try again.");
				return ERROR;
			}
		} else {
			System.out.println("User " + user.getUsername() + " opened My Settings.");
			return SUCCESS;
		}
	}

	protected HttpServletResponse servletResponse;

	protected HttpServletRequest servletRequest;

	@Override
	public void setUser(Author user) {
		this.user = user;
	}

	@Override
	public void setServletResponse(HttpServletResponse servletResponse) {
		this.servletResponse = servletResponse;
	}

	@Override
	public void setServletRequest(HttpServletRequest servletRequest) {
		this.servletRequest = servletRequest;
	}

	public boolean isAccount() {
		return account;
	}

	public void setAccount(boolean account) {
		this.account = account;
	}

	public boolean isPassword() {
		return password;
	}

	public void setPassword(boolean password) {
		this.password = password;
	}

	public boolean isSecurity() {
		return security;
	}

	public void setSecurity(boolean security) {
		this.security = security;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = Utils.removeBadChars(username);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email.toLowerCase();
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		if (role < 0)
			role = 0;
		if (role > 1)
			role = 1;
		this.role = role;
	}

	public String getPasswordOld() {
		return passwordOld;
	}

	public void setPasswordOld(String passwordOld) {
		this.passwordOld = passwordOld;
	}

	public String getPasswordNew() {
		return passwordNew;
	}

	public void setPasswordNew(String passwordNew) {
		this.passwordNew = passwordNew;
	}

	public String getPasswordVerify() {
		return passwordVerify;
	}

	public void setPasswordVerify(String passwordVerify) {
		this.passwordVerify = passwordVerify;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = Utils.removeBadChars(code);
	}
}