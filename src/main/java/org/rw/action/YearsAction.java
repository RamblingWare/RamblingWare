package org.rw.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.rw.action.model.Author;
import org.rw.action.model.UserAware;
import org.rw.config.Application;

import com.opensymphony.xwork2.ActionSupport;

/**
 * Years action class
 * @author Austin Delamar
 * @date 4/20/2017
 */
public class YearsAction extends ActionSupport implements UserAware, ServletResponseAware, ServletRequestAware {

	private static final long serialVersionUID = 1L;
	
	private ArrayList<String> years = new ArrayList<String>();
	
	public String execute() {
				
		// /year
		 
		// this shows all the years of blog posts
		try {
			// gather posts
			years = Application.getDatabaseSource().getArchiveYears();
			
			// already sorted chronologically
			
			// set attributes
			servletRequest.setCharacterEncoding("UTF-8");
			servletRequest.setAttribute("years", years);
			
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

	public ArrayList<String> getYears() {
		return years;
	}

	public void setYears(ArrayList<String> years) {
		this.years = years;
	}
}