package org.rw.action;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.rw.action.model.Author;
import org.rw.action.model.Post;
import org.rw.action.model.UserAware;
import org.rw.config.Application;

import com.opensymphony.xwork2.ActionSupport;

/**
 * View RSS action class
 * @author Austin Delamar
 * @date 12/9/2015
 */
public class RssAction extends ActionSupport implements UserAware, ServletResponseAware, ServletRequestAware {

	private static final long serialVersionUID = 1L;
	
	// no post parameters
	
	// search results
	private ArrayList<Post> posts = new ArrayList<Post>();
	
	public String execute() {
		
		// /rss
		
		// this page shows the RSS feed
		String response = "<?xml version=\"1.0\"?>"+
				"<rss version=\"2.0\">"+
				"<channel>"+
				"<title>RamblingWare Blog</title>"+
				"<description>This is my blog about computers, programming, tech, and things that bother me. I hope it bothers you too.</description>"+
				"<link>https://www.ramblingware.com</link>";
		try {
			// gather posts
			posts = Application.getDatabaseSource().getPosts(1, 10, false);
			
			for(Post post : posts) {
				response += "<item><title>"+post.getTitle()+"</title>"+
						"<description>"+post.getDescription()+"</description>"+
						"<pubDate>"+post.getPublishDateReadable()+"</pubDate>"+
						"<link>https://www.ramblingware.com/blog/"+post.getUriName()+"</link>"+
						"</item>";
			}
			response += "</channel></rss>";
			
			// set attributes
			servletRequest.setCharacterEncoding("UTF-8");
			servletResponse.setCharacterEncoding("UTF-8");
			
			PrintWriter out = null;
			try {
				// return message to user
				out = ServletActionContext.getResponse().getWriter();
				ServletActionContext.getResponse().setContentType("text/xml;charset=UTF-8");
				out.write(response);
			} catch (Exception e) {
				e.printStackTrace();
				addActionError("Error: "+e.getClass().getName()+". "+e.getMessage());
			}
	        // no action return
			return NONE;
		
		} catch (Exception e) {
			addActionError("Error: "+e.getClass().getName()+". Please try again later.");
			e.printStackTrace();
			return ERROR;
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
}