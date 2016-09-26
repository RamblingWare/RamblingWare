package org.amd.action;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.amd.bean.User;
import org.amd.bean.UserAware;
import org.amd.model.ApplicationStore;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

/**
 * View Archive action class
 * @author Austin Delamar
 * @date 11/30/2015
 */
public class ViewArchiveAction extends ActionSupport implements UserAware, ServletResponseAware, ServletRequestAware {

	private static final long serialVersionUID = 1L;
	
	// post parameters
	private ArrayList<String> archive_years;
	private ArrayList<String> archive_tags;
	
	public String execute() {
		
		try{ /* Try to set UTF-8 page encoding */
			servletRequest.setCharacterEncoding("UTF-8");
		} catch(Exception e) {
			System.err.println("Failed to set UTF-8 request encoding.");
		}
		
		// this page shows archive of posts by years and tag names
		Connection conn = null;
		try {
			conn = ApplicationStore.getConnection();
			Statement st = conn.createStatement();
			// search in db for total posts by each year
			ResultSet rs = st.executeQuery("select YEAR(create_date) as year, COUNT(*) as count from posts where is_visible <> 0 group by YEAR(create_date)");
			
			while(rs.next()) {
				// get year and  count
				int year = rs.getInt("year");
				int count = rs.getInt("count");
				archive_years.add(year+" ("+count+")");
			}
			
			// search in db for total tags by name
			ResultSet rs2 = st.executeQuery("select tag_name, COUNT(*) as count from tags group by tag_name");
			
			while(rs2.next()) {
				// get tag name and  count
				String tag = rs2.getString("tag_name");
				int count = rs2.getInt("count");
				archive_tags.add(tag+" ("+count+")");
			}
			
			// set attributes
			servletRequest.setAttribute("archive_years", archive_years);
			servletRequest.setAttribute("archive_tags", archive_tags);
			
			return Action.NONE;
			
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
		// TODO Auto-generated method stub
		
	}

	public ArrayList<String> getArchive_years() {
		return archive_years;
	}

	public void setArchive_years(ArrayList<String> archive_years) {
		this.archive_years = archive_years;
	}

	public ArrayList<String> getArchive_tags() {
		return archive_tags;
	}

	public void setArchive_tags(ArrayList<String> archive_tags) {
		this.archive_tags = archive_tags;
	}
}