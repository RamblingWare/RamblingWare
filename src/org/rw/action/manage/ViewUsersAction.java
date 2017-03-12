package org.rw.action.manage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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
				// get the user properties
				Author author = new Author(rs.getInt("user_id"));
				author.setUriName(rs.getString("uri_name"));
				author.setName(rs.getString("name"));
				author.setCreateDate(rs.getDate("create_date"));
				author.setThumbnail(rs.getString("thumbnail"));
				//author.setHtmlContent(rs.getString("html_content"));
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