package org.rw.action.manage;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.rw.action.model.Author;
import org.rw.action.model.UserAware;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
 
/**
 * Logout action class
 * @author Austin Delamar
 * @date 11/9/2015
 */
public class LogoutAction extends ActionSupport implements UserAware, ServletResponseAware, ServletRequestAware{
 
    private static final long serialVersionUID = 1L;
    public Author user;
    public Map<String, Object> sessionAttributes = null;
    
    private String message;
    
	// all struts logic here
	public String execute() {
		
		try{
			sessionAttributes = ActionContext.getContext().getSession();
			user = (Author) sessionAttributes.get("USER");
			System.out.println("User "+user.getUsername()+" logged out.");
			
			setMessage("You have been logged out.");
			
			sessionAttributes.remove("login");
			sessionAttributes.remove("context");
			sessionAttributes.remove("USER");
			
			return SUCCESS;
		} catch (Exception e) {
			addActionError("Failed to log out!");
			System.err.println("User "+user.getUsername()+" failed to log out for some reason. "+e.getMessage());
			
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

}