package org.rw.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.rw.action.model.Author;
import org.rw.action.model.Post;
import org.rw.action.model.UserAware;
import org.rw.config.Application;
import org.rw.config.Utils;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Year action class
 * @author Austin Delamar
 * @date 3/19/2017
 */
public class YearAction extends ActionSupport implements UserAware, ServletResponseAware, ServletRequestAware {

	private static final long serialVersionUID = 1L;
	
	private ArrayList<Post> posts = new ArrayList<Post>();
	private String year;
	
	public String execute() {
				
		// /year
		
		// this allows blog posts to be shown without parameter arguments (i.e. ?uri_name=foobar&test=123 )
		String  uriTemp = servletRequest.getRequestURI().toLowerCase();
		if(year == null && uriTemp.startsWith("/year/"))
			year = Utils.removeBadChars(uriTemp.substring(6,uriTemp.length()));
		else if(year == null && uriTemp.startsWith("/manage/year/"))
			year = Utils.removeBadChars(uriTemp.substring(13,uriTemp.length()));
				
		if(year != null && year.length() > 0)
		{
			try {
				// this shows the most recent blog posts by year
				int yr = Integer.parseInt(year);
				
				// gather posts
				posts = Application.getDatabaseSource().getPostsByYear(1, 25, yr, false);
				
				// set attributes
				servletRequest.setCharacterEncoding("UTF-8");
				servletRequest.setAttribute("posts", posts);
				
				return SUCCESS;
			
			} catch (Exception e) {
				addActionError("Error: "+e.getClass().getName()+". Please try again later.");
				e.printStackTrace();
				return ERROR;
			}
		}
		else
		{
			addActionError("Year '"+year+"' not recognized. Please try again.");
			return Action.NONE;
		}
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

	public ArrayList<Post> getPosts() {
		return posts;
	}

	public void setPosts(ArrayList<Post> posts) {
		this.posts = posts;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = Utils.removeBadChars(year);
	}
}