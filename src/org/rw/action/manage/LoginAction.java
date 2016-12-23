package org.rw.action.manage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.rw.bean.User;
import org.rw.bean.UserAware;
import org.rw.model.ApplicationStore;
import org.rw.model.PasswordHash;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
 
/**
 * Login action class
 * @author Austin Delamar
 * @date 11/9/2015
 */
public class LoginAction extends ActionSupport implements UserAware, ServletResponseAware, ServletRequestAware{
	 
    private static final long serialVersionUID = 1L;
    private User user;
    private Map<String, Object> sessionAttributes = null;
    private String username;
    private String password;
    private String code;
    
    private int lockoutPeriod = 30; // minutes
    private int maxAttempts = 3;
    private static int attempts = 0;
    private static long lastAttempt = 0;
    
    @Override
    public String execute(){
    	
    	// wait a bit, just to slow this request type down...
    	try {
    		attempts++;
    		Thread.sleep(500 * attempts);
		} catch (InterruptedException e1) {
			/* Don't bother to catch this exception */
		}
    	
    	// lockout for 30 min, if they failed 3 times
    	if(attempts > maxAttempts)
    	{
    		if(lastAttempt == 0)
    		{
    			// this is their 5th try, so record their time
    			lastAttempt = System.currentTimeMillis();
    			System.err.println("Unknown user has been locked out for "+lockoutPeriod+" min. ("+servletRequest.getRemoteAddr()+")("+servletRequest.getRemoteHost()+")");
    			addActionError("You have been locked out for the next "+lockoutPeriod+" minutes, for too many invalid attempts to login.");
    			return Action.ERROR;
    		}
    		else if(System.currentTimeMillis() >= (lastAttempt+(lockoutPeriod*60*1000)))
    		{
    			// its been 30mins or more, so unlock
    			attempts = 0;
    			lastAttempt = 0;
    			System.err.println("Unknown user has waited "+lockoutPeriod+" min, proceed. ("+servletRequest.getRemoteAddr()+")("+servletRequest.getRemoteHost()+")");
    			// continue
    		}
    		else
    		{
    			// they have already been locked out
    			System.err.println("Unknown user has been locked out for "+lockoutPeriod+" min. ("+servletRequest.getRemoteAddr()+")("+servletRequest.getRemoteHost()+") ");
    			addActionError("You have been locked out for the next "+lockoutPeriod+" minutes, for too many invalid attempts to login.");
    			return Action.ERROR;
    		}
    		
    	}
    	
    	// now try to see if they can login
    	if(username != null && password != null)
    	{
    		 try {
				Connection conn = ApplicationStore.getConnection();
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery("select * from users where username = '"+username+"'");
				
				if(rs.first()) 
				{
					user = new User();
					user.setUserId(rs.getString("user_id"));
		    		user.setEmail(rs.getString("email"));
		    		user.setName(rs.getString("name"));
		    		user.setFirstName(user.getName().substring(0,user.getName().indexOf(" ")));
		    		user.setUsername(rs.getString("username"));
		    		user.setUri_name(rs.getString("uri_name"));
		    		user.setAdmin(rs.getInt("role") > 0);
		    		user.setCreateDate(rs.getDate("create_date"));
		    		user.setLastLoginDate(rs.getDate("last_login_date"));
		    		user.setModifyDate(rs.getDate("modify_date"));
		    		user.setThumbnail(rs.getString("thumbnail"));
		    		
		    		ResultSet rs2 = st.executeQuery("select * from passwords where user_id = '"+user.getUserId()+"'");
		    		
		    		if(rs2.first() && PasswordHash.validatePassword(password, rs2.getString("pwd")))
					{
						// password matches! Login success
		    			
		    			// is OTP enabled?
		    			user.setOTPEnabled(rs2.getInt("is_otp_enabled") > 0);
		    			user.setOTPAuthenticated(!user.isOTPEnabled());
		    			
		    			if(user.isOTPEnabled())
		    			{
		    				// user needs to enter a OTP still before
		    				// they are logged in fully.
		    				attempts = 0;
		        			lastAttempt = 0;
			    			sessionAttributes = ActionContext.getContext().getSession();
				    		sessionAttributes.put("USER", user);
				    		
				    		System.out.println("User logged in "+user.getUsername()+" and now has to enter their OTP ("+servletRequest.getRemoteAddr()+")");
				    		
				    		return INPUT;
		    			}
		    			else
		    			{
		    				// user didn't enable OTP / 2FA yet.
		    				
		    				// update last login date
			    			st.executeUpdate("update users set last_login_date='"
									+ApplicationStore.formatMySQLDate(new Date(System.currentTimeMillis()))+"'"
											+ "where user_id = '"+user.getUserId()+"'");
			    			
			    			sessionAttributes = ActionContext.getContext().getSession();
				    		sessionAttributes.put("login","true");
				    		sessionAttributes.put("context", new Date());
				    		sessionAttributes.put("USER", user);
				    		
				    		addActionMessage("Welcome back, "+user.getName()+". Last login was on "+ApplicationStore.formatReadableDate(user.getLastLoginDate()));
				    		System.out.println("User logged in: "+user.getUsername()+" ("+servletRequest.getRemoteAddr()+")");
				    		
				    		return SUCCESS;
		    			}
					}
		    		else
					{
						// password did not match
						addActionMessage("Invalid login. ("+attempts+"/"+maxAttempts+")");
						System.out.println("User failed to login. Password did not match. ("+attempts+"/"+maxAttempts+") ("+servletRequest.getRemoteAddr()+")");
					}
				}
				else
				{
					// no user found
					addActionMessage("Invalid login. ("+attempts+"/"+maxAttempts+")");
					System.out.println("User failed to login. Invalid Username was entered "+username+" ("+attempts+"/"+maxAttempts+") ("+servletRequest.getRemoteAddr()+")");
				}
			} catch (Exception e) {
				addActionError(e.getMessage());
				e.printStackTrace();
			}
    	}
    	else if(code != null) 
    	{
    		// two factor code provided
    		// verify if it is correct
    		// TODO
    		System.out.println("Code = "+code);
    		
    		sessionAttributes = ActionContext.getContext().getSession();
    		user = (User) sessionAttributes.get("USER");
    		
    		if(code.equals("123456")) {
	    		user.setOTPAuthenticated(true);
	    		sessionAttributes.put("login","true");
	    		sessionAttributes.put("context", new Date());
	    		sessionAttributes.put("USER", user);
	    		attempts = 0;
    			lastAttempt = 0;
	    		
	    		try {
	 				Connection conn = ApplicationStore.getConnection();
	 				Statement st = conn.createStatement();
	    		
		    		// update last login date
					st.executeUpdate("update users set last_login_date='"
							+ApplicationStore.formatMySQLDate(new Date(System.currentTimeMillis()))+"'"
									+ "where user_id = '"+user.getUserId()+"'");
		    		
		    		addActionMessage("Welcome back, "+user.getName()+". Last login was on "+ApplicationStore.formatReadableDate(user.getLastLoginDate()));
		    		System.out.println("User logged in: "+user.getUsername()+" with their OTP ("+servletRequest.getRemoteAddr()+")");
		    	
	    		} catch (Exception e) {
	 				addActionError(e.getMessage());
	 				e.printStackTrace();
	 			}
	    		return SUCCESS;
	    		
    		} else {
    			// OTP code did not match!
    			addActionMessage("Invalid login. ("+attempts+"/"+maxAttempts+")");
    			System.out.println("User tried to login with OTP: "+user.getUsername()+" ("+attempts+"/"+maxAttempts+") ("+servletRequest.getRemoteAddr()+")");
    			
    			return INPUT;
    		}
    	}
    	else
    	{
    		// invalid login
    		addActionMessage("Invalid login. ("+attempts+"/"+maxAttempts+")");
    		System.out.println("User tried to login with an invalid Username. ("+attempts+"/"+maxAttempts+") ("+servletRequest.getRemoteAddr()+")");
    	}
        return ERROR;
    }
    
    public void setSession(Map<String, Object> sessionAttributes) {
        this.sessionAttributes = sessionAttributes;
    }

	@Override
	public void setUser(User user) {
		this.user = user;
	}
	
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
		this.username = username;
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
		this.code = code;
	}

	public int getAttempts() {
		return attempts;
	}
     
}