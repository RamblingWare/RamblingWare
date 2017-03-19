package org.rw.action;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.rw.bean.Author;
import org.rw.bean.UserAware;
import org.rw.model.ApplicationStore;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

/**
 * View Author action class
 * @author Austin Delamar
 * @date 10/23/2016
 */
public class ViewAuthorAction extends ActionSupport implements UserAware, ServletResponseAware, ServletRequestAware {

	private static final long serialVersionUID = 1L;
	
	// post parameters
	private Author author;
	private String uri_name;
	
	public String execute() {
		
		// /author/person-name-goes-here
		String  uri = servletRequest.getRequestURI();
		if(uri_name == null && uri.startsWith("/author/"))
			uri_name = ApplicationStore.removeBadChars(uri.substring(8,uri.length()));
		
		if(uri_name != null && uri_name.length() > 0)
		{
			// search in db for author
			try {
				author = ApplicationStore.getDatabaseSource().getAuthor(uri_name);
				
				if(author != null)
				{					
					// set attributes
					servletRequest.setCharacterEncoding("UTF-8");
					servletRequest.setAttribute("author", author);
					
					return Action.SUCCESS;
				}
				else
				{
					addActionError("Author '"+uri_name+"' not found. Please try again.");
					return Action.NONE;
				}
			
			} catch (Exception e) {
				addActionError("Error: "+e.getClass().getName()+". Please try again later.");
				e.printStackTrace();
				return ERROR;
			}
		}
		else
		{
			addActionError("Author '"+uri_name+"' not found. Please try again.");
			return Action.NONE;
		}
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
		
		// cookie will last 1 year
		cookie.setMaxAge(60 * 60 * 24 * 365);
		servletResponse.addCookie(cookie);
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
	
	@Override
	public void setUser(Author user) {
		// TODO Auto-generated method stub
		
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public Author getAuthor() {
		return author;
	}
	
}