package org.amd.action.manage;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.amd.bean.User;
import org.amd.bean.UserAware;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
 
/**
 * Logout action class
 * @author Austin Delamar
 * @date 11/9/2015
 */
public class LogoutAction extends ActionSupport implements UserAware, ServletResponseAware, ServletRequestAware{
 
    private static final long serialVersionUID = 1L;
    public User user;
    public Map<String, Object> sessionAttributes = null;
    
    private String message;
    
	// all struts logic here
	public String execute() {
		
		try{
			sessionAttributes = ActionContext.getContext().getSession();
			user = (User) sessionAttributes.get("USER");
			System.out.println("User \""+user.getUsername()+"\" logged out");
			
			setMessage("You have been logged out.");
			
			sessionAttributes.remove("login");
			sessionAttributes.remove("context");
			sessionAttributes.remove("USER");
			
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			addActionError("Failed to log out!");
			System.out.println("User \""+user.getUsername()+"\" failed to log out.");
			
			return ERROR;
		}
 
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
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

}