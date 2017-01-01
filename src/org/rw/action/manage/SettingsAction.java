package org.rw.action.manage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.rw.bean.User;
import org.rw.bean.UserAware;
import org.rw.model.ApplicationStore;
import org.rw.model.PasswordHash;
import org.rw.model.TwoFactor;

import com.opensymphony.xwork2.ActionSupport;

/**
 * Settings action class
 * @author Austin Delamar
 * @date 12/19/2016
 */
public class SettingsAction extends ActionSupport implements UserAware, ServletResponseAware, ServletRequestAware {

	private static final long serialVersionUID = 1L;
	private User user;
	
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
		
		try{ /* Try to set UTF-8 page encoding */
			servletRequest.setCharacterEncoding("UTF-8");
		} catch(Exception e) {
			System.err.println("Failed to set UTF-8 request encoding.");
		}		
		
		if(!user.isOTPEnabled() && user.getKeySecret() == null)
		{
			// generate secret
			secret = TwoFactor.randomBase32();
			user.setKeySecret(secret);
		}
		else
		{
			// remember the generated secret
			secret = user.getKeySecret();
		}
		
		if(account)
		{
			// they've changed their account info
			
			// validate inputs
			if(!ApplicationStore.isValidEmail(email) || email.isEmpty())
			{
				addActionError("Email address is not valid. Please try again.");
				return ERROR;
			}
			if(username.isEmpty())
			{
				addActionError("Username is not valid. Please try again.");
				return ERROR;
			}
			
			// update user information
			Connection conn = null;
			Statement st = null;
			try {
				conn = ApplicationStore.getConnection();
				st = conn.createStatement();
				conn.setAutoCommit(false);
				
				String update = "update users set "
						+ "username = ?,"
						+ "email = ?,"
						+ "modify_date = ? where user_id = "+user.getUserId();
				PreparedStatement pt = conn.prepareStatement(update);
				pt.setString(1, username);
				pt.setString(2, email);
				pt.setDate(3, new java.sql.Date(System.currentTimeMillis()));
				
				if(pt.execute())
				{
					// failed to update user
					conn.rollback();
					addActionError("Failed to update account. Please try again.");
					System.out.println("Failed to update the user. "+user.getUsername());
					return ERROR;
				}
				
				// done
				conn.commit();
				
			} catch (Exception e) {
				try {
					conn.rollback();
				} catch (SQLException e1) {}
				addActionError("An error occurred: "+e.getMessage());
				e.printStackTrace();
				return ERROR;
			} finally {
				try {
					st.close();
					conn.close();
				} catch (Exception e) {}
			}
			
			user.setUsername(username);
			user.setEmail(email);			
			System.out.println("User "+user.getUsername()+" updated their account.");
			addActionMessage("Changes were saved.");
			return SUCCESS;
		}
		else if(password)
		{
			// they're changing their password
			
			// validate inputs
			if(passwordOld == null || passwordOld.isEmpty())
			{
				addActionError("Current Password is not valid. Please try again.");
				return ERROR;
			}
			if(passwordNew == null || passwordNew.isEmpty())
			{
				addActionError("New Password is not valid. Please try again.");
				return ERROR;
			}
			if(passwordVerify == null || passwordVerify.isEmpty())
			{
				addActionError("New Password is not valid. Please try again.");
				return ERROR;
			}
			if(passwordNew.length() < 8)
			{
				addActionError("New Password must be 8 or more characters. Please try again.");
				return ERROR;
			}
			if(!passwordNew.equals(passwordVerify))
			{
				addActionError("New Password did not match! Please try again.");
				return ERROR;
			}
			
			// update user information
			Connection conn = null;
			Statement st = null;
			try {
				conn = ApplicationStore.getConnection();
				st = conn.createStatement();
				conn.setAutoCommit(false);
				ResultSet rs = st.executeQuery("select * from passwords where user_id = '"+user.getUserId()+"'");
				
				if(rs.first() && PasswordHash.validatePassword(passwordOld, rs.getString("pwd"))) 
				{
					// old password matches, so update with new password
	    			
					String update = "update passwords set "
							+ "pwd = ? where user_id = "+user.getUserId();
					PreparedStatement pt = conn.prepareStatement(update);
					
					// salt and hash the password
					pt.setString(1, PasswordHash.createHash(passwordNew));
					
					if(pt.execute())
					{
						// failed to update user password
						conn.rollback();
						addActionError("Failed to update your password. Please try again.");
						System.out.println("Failed to update the user. "+user.getUsername());
						return ERROR;
					}
					
					// done
					conn.commit();
				}
				else
				{
					// password does not match
					addActionMessage("Invalid Password.");
					System.out.println("User "+user.getUsername()+" failed to change password. Invalid Password was entered.");
				}
			} catch (Exception e) {
				addActionError("Failed to update your password. Please try again.");
				e.printStackTrace();
				return ERROR;
			} finally {
				try {
					st.close();
					conn.close();
				} catch (Exception e) {}
			}			
			
			System.out.println("User "+user.getUsername()+" updated their password.");
			addActionMessage("Password was saved.");
			return SUCCESS;
		}
		else if(security)
		{
			// they're changing their security
			
			if(passwordOld != null)
			{
				// they want to disable 2FA.
				// validate inputs
				if(passwordOld.isEmpty())
				{
					addActionError("Password is not valid. Please try again.");
					return ERROR;
				}
				if(!ApplicationStore.removeAllSpaces(code).equals(ApplicationStore.removeAllSpaces(user.getKeyRecover())))
				{
					addActionError("Recovery Code is not valid. Please try again.");
					return ERROR;
				}
				// update user information
				Connection conn = null;
				Statement st = null;
				try {
					conn = ApplicationStore.getConnection();
					st = conn.createStatement();
					conn.setAutoCommit(false);
					ResultSet rs = st.executeQuery("select * from passwords where user_id = '"+user.getUserId()+"'");
					
					if(rs.first() && PasswordHash.validatePassword(passwordOld, rs.getString("pwd"))) 
					{
						// old password matches, so disable 2FA
						String update = "update passwords set "
								+ "is_otp_enabled = false,"
								+ "otp_key = ?,"
								+ "recover_key = ? where user_id = "+user.getUserId();
						PreparedStatement pt = conn.prepareStatement(update);
						pt.setNull(1, Types.VARCHAR);
						pt.setNull(2, Types.VARCHAR);
						
						if(pt.executeUpdate() < 1)
						{
							// failed to update user security
							conn.rollback();
							addActionError("Failed to disable 2FA. Please try again.");
							System.out.println("Failed to disable 2FA for user "+user.getUsername());
							return ERROR;
						}
						
						// done
						conn.commit();
					}
					else
					{
						// password does not match
						addActionError("Invalid Password. Please try again.");
						System.out.println("User "+user.getUsername()+" failed to disable 2FA. Invalid Password was entered.");
						return ERROR;
					}
				} catch (Exception e) {
					addActionError("Failed to disable 2FA. Please try again.");
					e.printStackTrace();
					return ERROR;
				} finally {
					try {
						st.close();
						conn.close();
					} catch (Exception e) {}
				}		
				
				// update user settings in session
				user.setOTPEnabled(false);
				user.setOTPAuthenticated(true);
				user.setKeySecret(null);
				user.setKeyRecover(null);
				
				System.out.println("User "+user.getUsername()+" successfully disabled 2FA.");
				addActionMessage("Two Factor Authentication was disabled!");
				return SUCCESS;
			}
			else if(TwoFactor.validateTOTP(secret, code))
			{
				// they want to enable 2FA.
				// generate recovery key
				String recover = TwoFactor.randomString("ABCDEFGHIJKLMNOPQRSTUVWXYZ234567",12);
				recover = recover.substring(0, 3)+" "+recover.substring(3,6)+" "+recover.substring(6,9)+" "+recover.substring(9);
				
				// update user information
				Connection conn = null;
				Statement st = null;
				try {
					conn = ApplicationStore.getConnection();
					st = conn.createStatement();
					conn.setAutoCommit(false);
					
					String update = "update passwords set "
							+ "is_otp_enabled = true,"
							+ "otp_key = ?,"
							+ "recover_key = ? where user_id = "+user.getUserId();
					PreparedStatement pt = conn.prepareStatement(update);
					pt.setString(1, secret);
					pt.setString(2, recover);
					
					if(pt.execute())
					{
						// failed to update user security
						conn.rollback();
						addActionError("Failed to enable 2FA. Please try again.");
						System.out.println("Failed to enable 2FA for user "+user.getUsername());
						return ERROR;
					}
					
					// done
					conn.commit();
					
				} catch (Exception e) {
					try {
						conn.rollback();
					} catch (SQLException e1) {}
					addActionError("An error occurred: "+e.getMessage());
					e.printStackTrace();
					return ERROR;
				} finally {
					try {
						st.close();
						conn.close();
					} catch (Exception e) {}
				}
				
				// update user settings in session
				user.setOTPEnabled(true);
				user.setOTPAuthenticated(false);
				user.setKeySecret(secret);
				user.setKeyRecover(recover);
				
				System.out.println("User "+user.getUsername()+" successfully enabled 2FA.");
				addActionMessage("Two Factor Authentication was successfully enabled!");
				return SUCCESS;
			}
			else
			{
				System.out.println("User's Security Code did not match the generated code.");
				addActionError("Security Code Invalid. Please try again.");
				return ERROR;
			}
		}
		else
		{
			System.out.println("User "+user.getUsername()+" opened My Settings.");
			return SUCCESS;
		}		
	}

	protected HttpServletResponse servletResponse;

	protected HttpServletRequest servletRequest;

	@Override
	public void setUser(User user) {
		this.user = user;		
	}

	/**
	 * Return a cookie's value by its given name.
	 * @param cookieName
	 * @return Cookie
	 */
	public Cookie getCookie(String cookieName) {
		Cookie cookies[] = servletRequest.getCookies();
		Cookie myCookie = null;
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals(cookieName)) {
					myCookie = cookies[i];
					break;
				}
			}
		}
		return myCookie;
	}

	/**
	 * Sets a cookie's value for the given name.
	 * @param cookieName
	 * @param cookieValue
	 */
	public void setCookie(String cookieName, String cookieValue) {
		Cookie cookie = new Cookie(cookieName, cookieValue);
		cookie.setPath("/");
		// cookie will last 1 year
		cookie.setMaxAge(60 * 60 * 24 * 365);
		servletResponse.addCookie(cookie);
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
		this.username = ApplicationStore.removeBadChars(username);
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
		if(role < 0)
			role = 0;
		if(role > 1)
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
		this.code = ApplicationStore.removeBadChars(code);
	}
}