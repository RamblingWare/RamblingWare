package org.rw.action.manage;

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

import com.opensymphony.xwork2.ActionSupport;

/**
 * View/Edit Posts action class
 * @author Austin Delamar
 * @date 5/30/2016
 */
public class ViewPostsAction extends ActionSupport implements UserAware, ServletResponseAware, ServletRequestAware {

	private static final long serialVersionUID = 1L;
	
	// results
	private ArrayList<Post> posts;
	private int page;
	private int limit = 7;
	private boolean nextPage;
	private boolean prevPage;
	
	public String execute() {
		
		// /manage/posts
		
		try {
			// jump to page if provided
			String  pageTemp = servletRequest.getRequestURI().toLowerCase();
			if(pageTemp.startsWith("/manage/posts/page/"))
			{
				pageTemp = Utils.removeBadChars(pageTemp.substring(19,pageTemp.length()));
				page = Integer.parseInt(pageTemp);
			} else {
				page = 1;
			}
			
			// gather posts
			posts = Application.getDatabaseSource().getPosts(page, limit, true);
			
			// determine pagination
			nextPage = posts.size() <= limit;
			prevPage = page > 1;
			
			// set attributes
			servletRequest.setCharacterEncoding("UTF-8");
			servletRequest.setAttribute("posts", posts);
			servletRequest.setAttribute("page", page);
			servletRequest.setAttribute("nextPage", nextPage);
			servletRequest.setAttribute("prevPage", prevPage);
			
			return SUCCESS;
		
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

	public ArrayList<Post> getPosts() {
		return posts;
	}

	public void setPosts(ArrayList<Post> posts) {
		this.posts = posts;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public boolean isNextPage() {
		return nextPage;
	}

	public void setNextPage(boolean nextPage) {
		this.nextPage = nextPage;
	}

	public boolean isPrevPage() {
		return prevPage;
	}

	public void setPrevPage(boolean prevPage) {
		this.prevPage = prevPage;
	}
}