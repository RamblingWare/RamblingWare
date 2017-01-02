package org.rw.action.manage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.rw.bean.Author;
import org.rw.bean.User;
import org.rw.bean.UserAware;
import org.rw.model.ApplicationStore;

import com.opensymphony.xwork2.ActionSupport;

/**
 * View/Edit Users action class
 * @author Austin Delamar
 * @date 10/23/2016
 */
public class ViewUsersAction extends ActionSupport implements UserAware, ServletResponseAware, ServletRequestAware {

	private static final long serialVersionUID = 1L;
	private User user;
	
	// results
	private ArrayList<Author> results = new ArrayList<Author>();
	
	public String execute() {
		
		try{ /* Try to set UTF-8 page encoding */
			servletRequest.setCharacterEncoding("UTF-8");
		} catch(Exception e) {
			System.err.println("Failed to set UTF-8 request encoding.");
		}
		
		// /blog
		
		// this shows the blog posts
		System.out.println("User "+user.getUsername()+" has requested to view all users.");
		Connection conn = null;
		try {
			conn = ApplicationStore.getConnection();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("select user_id, name, thumbnail, uri_name, email, role, create_date, modify_date, last_login_date from users order by create_date desc");
			
			while(rs.next()) {
				
				// get the author properties
				int user_id = rs.getInt("user_id");
				String name = rs.getString("name");
				Date create_date = rs.getDate("create_date");
				String uri_name = rs.getString("uri_name");
				
				// save info into an object
				Author author = new Author(user_id, uri_name, name, create_date);
				author.setThumbnail(rs.getString("thumbnail"));
				author.setEmail(rs.getString("email"));
				author.setAdmin(rs.getInt("role") > 0);
				author.setModifyDate(rs.getDate("modify_date"));
				author.setLastLoginDate(rs.getDate("last_login_date"));
				
				
				// add to results list
				results.add(author);
			}
			
			servletRequest.setAttribute("results", results);
			
			return SUCCESS;
		
		} catch (Exception e) {
			addActionError("Error: "+e.getClass().getName()+". Please try again later.");
			e.printStackTrace();
			return ERROR;
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {/*Do Nothing*/}
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
		cookie.setPath("/");
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
	public void setUser(User user) {
		this.user = user;		
	}

	public ArrayList<Author> getResults() {
		return results;
	}

	public void setResults(ArrayList<Author> results) {
		this.results = results;
	}
}