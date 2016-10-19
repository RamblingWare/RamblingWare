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
    private String email;
    private String password;
    
    private int maxAttempts = 5;
    private static int attempts = 0;
    private static long lastAttempt = 0;
    
    @Override
    public String execute(){
    	
    	// wait a bit, just to slow this request type down...
    	try {
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
    			System.out.println("Unknown user has been locked out for 30 min. ("+servletRequest.getRemoteAddr()+")");
    			addActionError("You have been locked out for the next 30 minutes, for too many invalid attempts to login.");
    			return Action.ERROR;
    		}
    		else if(System.currentTimeMillis() >= (lastAttempt+(30*60*1000)))
    		{
    			// its been 30mins or more, so unlock
    			attempts = 0;
    			lastAttempt = 0;
    			System.out.println("Unknown user has waited 30min, proceed. ("+servletRequest.getRemoteAddr()+")");
    			// continue
    		}
    		else
    		{
    			// they have already been locked out
    			System.out.println("Unknown user has been locked out for 30 min. ("+servletRequest.getRemoteAddr()+")");
    			addActionError("You have been locked out for the next 30 minutes, for too many invalid attempts to login.");
    			return Action.ERROR;
    		}
    		
    	}
    	
    	// now try to see if they can login
    	if(user == null && email != null && ApplicationStore.isValidEmail(email) && password != null)
    	{
    		 try {
				Connection conn = ApplicationStore.getConnection();
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery("select * from users where email = '"+email+"'");
				
				if(rs.first()) 
				{
					user = new User();
		    		user.setEmail(email);
		    		user.setName(rs.getString("name"));
		    		user.setFirstName(user.getName().substring(0,user.getName().indexOf(" ")));
		    		user.setUsername(email.substring(0,email.indexOf('@')));
		    		user.setUserId(rs.getString("user_id"));
		    		user.setUri_name(rs.getString("uri_name"));
		    		user.setCreateDate(rs.getDate("create_date"));
		    		user.setLastLoginDate(rs.getDate("last_login_date"));
		    		
		    		ResultSet rs2 = st.executeQuery("select * from passwords where user_id = '"+user.getUserId()+"'");
		    		
		    		if(rs2.first() && PasswordHash.validatePassword(password, rs2.getString("pwd")))
					{
						// password matches! Login success
		    			
		    			// update last login date
		    			st.executeUpdate("update users set last_login_date='"
								+ApplicationStore.formatMySQLDate(new Date(System.currentTimeMillis()))+"'"
										+ "where user_id = '"+user.getUserId()+"'");
		    			
		    			sessionAttributes = ActionContext.getContext().getSession();
			    		sessionAttributes.put("login","true");
			    		sessionAttributes.put("context", new Date());
			    		sessionAttributes.put("USER", user);
			    		
			    		addActionMessage("Welcome back, "+user.getFirstName()+". Last login was on "+ApplicationStore.formatReadableDate(user.getLastLoginDate()));
			    		System.out.println("User logged in: "+user.getUsername()+" ("+servletRequest.getRemoteAddr()+")");
			    		 
			    		return SUCCESS;
					}
		    		else
					{
						// password did not match
		    			attempts++;
						addActionError("Invalid login. ("+attempts+"/"+maxAttempts+")");
						System.out.println("User failed to login. Invalid Email or Password. ("+attempts+"/"+maxAttempts+") ("+servletRequest.getRemoteAddr()+")");
					}
				}
				else
				{
					// no user found
					attempts++;
					addActionError("Invalid login. ("+attempts+"/"+maxAttempts+")");
					System.out.println("User failed to login. Invalid Email was entered "+email+" ("+attempts+"/"+maxAttempts+") ("+servletRequest.getRemoteAddr()+")");
				}
			} catch (Exception e) {
				addActionError(e.getMessage());
				e.printStackTrace();
			}
    	}
    	else
    	{
    		// invalid Email Address
    		attempts++;
    		addActionError("Invalid login. ("+attempts+"/"+maxAttempts+")");
    		System.out.println("User tried to login with an invalid Email Address. ("+attempts+"/"+maxAttempts+") ("+servletRequest.getRemoteAddr()+")");
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email.toLowerCase();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getAttempts() {
		return attempts;
	}
     
}