package org.rw.action.manage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.rw.action.model.Author;
import org.rw.action.model.UserAware;
import org.rw.config.Application;
import org.rw.config.Utils;
import org.rw.security.PasswordHash;

import com.opensymphony.xwork2.ActionSupport;

/**
 * New User action class
 * 
 * @author Austin Delamar
 * @date 11/9/2015
 */
public class NewUserAction extends ActionSupport implements UserAware, ServletResponseAware, ServletRequestAware {

	private static final long serialVersionUID = 1L;
	private Author user;

	private String username;
	private String uriName;
	private String email;
	private String name;
	private String password;
	private String password2;

	@Override
	public String execute() {

		try { /* Try to set UTF-8 page encoding */
			servletRequest.setCharacterEncoding("UTF-8");
		} catch (Exception e) {
			System.err.println("Failed to set UTF-8 request encoding.");
		}

		if (servletRequest.getParameter("submitForm") != null) {
			// submitted new user!

			if (email != null && Utils.isValidEmail(email)) {
				if (password != null && password2 != null && password.equals(password2)) {
					// passwords match
					if (password.length() >= 8) {
						// password is long enough
						
						try {
							// check if user exists or not
							if (Application.getDatabaseSource().getAuthor(uriName) == null) {
								// user does not already exist.
								// add new user
								// salt and hash the password
								password = PasswordHash.createHash(password);

								Author newUser = new Author(-1);
								newUser.setEmail(email);
								newUser.setName(name);
								newUser.setUsername(username);
								newUser.setUriName(uriName);
								newUser.setPassword(password);
								
								// insert into database
								newUser = Application.getDatabaseSource().newUser(newUser);
								
								if (newUser.getId() != -1) {
									// Successfully registered new user!

									addActionMessage("Successfully created new Author.");
									System.out.println("User "+user.getUsername()+" created new author: " + username);
									return "edit";
								}
								else {
									// failed to insert new user
									addActionError("Failed to create new author. ");
									System.out.println("Failed to create new author. "+username);
									return ERROR;
								}
							} else {
								// user is already registered
								addActionError("Invalid Email Address.");
								System.out.println("User tried to register with a Email that already exists!");
								return ERROR;
							}
						} catch (Exception e) {
							addActionError("Failed to create new author: "+e.getMessage());
							e.printStackTrace();
							return ERROR;
						}
					} else {
						// password is too short
						addActionError("Password needs to be at least 8 characters long. Please try again.");
						System.out.println("User tried to register with a short password.");
						return ERROR;
					}
				} else {
					// passwords do not match
					addActionError("Passwords do not match. Please try again.");
					System.out.println("User tried to register with non-matching passwords.");
					return ERROR;
				}
			} else {
				// invalid Email Address
				addActionError("Invalid Email Address. Please try again.");
				System.out.println("User tried to register with an invalid Email Address.");
				return ERROR;
			}
		}

		// no submit yet
		System.out.println("User " + user.getUsername() + " opened new user.");
		return SUCCESS;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email.toLowerCase();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = Utils.removeNonAsciiChars(name);
	}

	public String getUriName() {
		return uriName;
	}

	public void setUriName(String uriName) {
		this.uriName = Utils.removeAllSpaces(uriName.trim().toLowerCase());
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}

}