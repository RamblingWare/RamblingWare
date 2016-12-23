package org.rw.action.manage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.rw.bean.User;
import org.rw.bean.UserAware;
import org.rw.model.ApplicationStore;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
 
/**
 * Forgot action class
 * @author Austin Delamar
 * @date 12/19/2016
 */
public class ForgotAction extends ActionSupport implements UserAware, ServletResponseAware, ServletRequestAware{
	 
    private static final long serialVersionUID = 1L;
    private User user;
    private Map<String, Object> sessionAttributes = null;
    
    private String email; // need email to send reminders or resets
    private String type; // 'username' or 'password'
    private boolean remind; // forgot username
    private boolean reset; // forgot password
    private String token;
    
    private int lockoutPeriod = 30; // minutes
    private int maxAttempts = 3;
    private static int attempts = 0;
    private static long lastAttempt = 0;
    
    @Override
    public String execute(){
    	
    	if(type == null)
    		type = "username";
    	
    	// now try to see if they can remember their email
    	if((remind || reset) && email != null && ApplicationStore.isValidEmail(email))
    	{
    		// wait a bit, just to slow this request type down...
        	try {
        		attempts++;
        		Thread.sleep(500 * attempts);
    		} catch (InterruptedException e1) {
    			/* Don't bother to catch this exception */
    		}
        	
        	// lockout for 30 min, if they failed 3 times
        	if(attempts >= maxAttempts)
        	{
        		if(lastAttempt == 0)
        		{
        			// this is their 5th try, so record their time
        			lastAttempt = System.currentTimeMillis();
        			System.err.println("Unknown user has been locked out for "+lockoutPeriod+" min. ("+servletRequest.getRemoteAddr()+")("+servletRequest.getRemoteHost()+")");
        			addActionError("You have been locked out for the next "+lockoutPeriod+" minutes, for too many attempts.");
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
        			addActionError("You have been locked out for the next "+lockoutPeriod+" minutes, for too many attempts.");
        			return Action.ERROR;
        		}
        		
        	}
    		
    		
    		try {
				Connection conn = ApplicationStore.getConnection();
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery("select * from users where email = '"+email+"'");
				
				if(rs.first()) 
				{
					user = new User();
		    		user.setEmail(email);
		    		user.setUsername(email.substring(0,email.indexOf('@')));
		    		
		    		// TODO
		    		// if email is good, and remind, then send reminder email
		    		
		    		// if email is good, and reset, then send email link to reset password
		    		// generate a unique token that can only be used once, without login.
		    		
		    		if(remind)
		    			addActionMessage("You have been sent a reminder. Please check your inbox for your Username.");
		    		else if(reset)
		    			addActionMessage("You have been sent a reset token. Please check your inbox to reset your password.");
		    		
		    		attempts = 0;
		    		
		    		return SUCCESS;
				}
				else
				{
					// no user found
					addActionMessage("Invalid Email Address. ("+attempts+"/"+maxAttempts+")");
					System.out.println("User failed to recover. Invalid Email was entered "+email+" ("+attempts+"/"+maxAttempts+") ("+servletRequest.getRemoteAddr()+")");
				}
			} catch (Exception e) {
				addActionError(e.getMessage());
				e.printStackTrace();
			}
    	}
    	else if(remind || reset)
    	{
    		// invalid Email Address
    		addActionMessage("Invalid Email Address. ("+attempts+"/"+maxAttempts+")");
    		System.out.println("User tried to recover with an invalid Email Address. ("+attempts+"/"+maxAttempts+") ("+servletRequest.getRemoteAddr()+")");
    		return ERROR;
    	}
        
    	return NONE;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email.toLowerCase();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
     
}