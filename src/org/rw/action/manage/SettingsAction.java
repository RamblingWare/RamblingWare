package org.rw.action.manage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.rw.bean.User;
import org.rw.bean.UserAware;
import org.rw.model.ApplicationStore;

import com.opensymphony.xwork2.ActionSupport;

/**
 * Settings action class
 * @author Austin Delamar
 * @date 12/19/2016
 */
public class SettingsAction extends ActionSupport implements UserAware, ServletResponseAware, ServletRequestAware {

	private static final long serialVersionUID = 1L;
	private User user;
	
	// updated values
	private String name;
    private String uriName;
    private String email;
    
    private String thumbnail;
    private String description;
    private String htmlContent;
	
	public String execute() {
		
		try{ /* Try to set UTF-8 page encoding */
			servletRequest.setCharacterEncoding("UTF-8");
		} catch(Exception e) {
			System.err.println("Failed to set UTF-8 request encoding.");
		}
		
		if(servletRequest.getParameter("submit")!=null)
		{
			// they've submitted an edit on a user
			Connection conn = null;
			Statement st = null;
			try {
				conn = ApplicationStore.getConnection();
				st = conn.createStatement();
				conn.setAutoCommit(false);
				
				String update = "update users set "
						+ "name = ?,"
						+ "uri_name = ?,"
						+ "modify_date = ?,"
						+ "thumbnail = ?,"
						+ "description = ?,"
						+ "html_content = ? where user_id = "+user.getUserId();
				PreparedStatement pt = conn.prepareStatement(update);
				pt.setString(1, name);
				pt.setString(2, uriName);
				pt.setDate(3, new java.sql.Date(System.currentTimeMillis()));
				pt.setString(4, thumbnail);
				pt.setString(5, description);
				pt.setString(6, htmlContent);
				
				if(pt.execute())
				{
					// failed to update user
					conn.rollback();
					addActionError("Oops. Failed to update the user. Please try again.");
					System.out.println("Failed to update the user. "+uriName);
					return ERROR;
				}
				
				// done
				conn.commit();
				
			} catch (Exception e) {
				try {
					conn.rollback();
				} catch (SQLException e1) {}
				addActionError("An error occurred: "+e.getMessage());
				e.printStackTrace();
				return ERROR;
			} finally {
				try {
					st.close();
					conn.close();
				} catch (Exception e) {}
			}
			
			System.out.println("User "+user.getUsername()+" updated user: "+user.getName());
			addActionMessage("Successfully saved the changes to the user.");
			return "edit";
		}
		else if(servletRequest.getParameter("delete")!=null)
		{
			// they've requested to delete a user
			Connection conn = null;
			Statement st = null;
			try {
				conn = ApplicationStore.getConnection();
				st = conn.createStatement();
				conn.setAutoCommit(false);
				
				int r = st.executeUpdate("delete from users where user_id = "+user.getUserId());
				
				if(r == 0) {
					conn.rollback();
					addActionError("Oops. Failed to delete the user. Please try again.");
					System.out.println("Failed to delete user: "+user.getUserId());
					return ERROR;
				}
				
				// done
				conn.commit();
				
			} catch (Exception e) {
				try {
					conn.rollback();
				} catch (SQLException e1) {}
				addActionError("An error occurred: "+e.getMessage());
				e.printStackTrace();
				return ERROR;
			} finally {
				try {
					st.close();
					conn.close();
				} catch (Exception e) {}
			}
			
			System.out.println("User "+user.getUsername()+" deleted user: "+user.getName());
			addActionMessage("The user was deleted!");
			return "edit";
		}
		
		System.out.println("User "+user.getUsername()+" opened My Settings.");
		return SUCCESS;
	}

	protected HttpServletResponse servletResponse;

	protected HttpServletRequest servletRequest;

	@Override
	public void setUser(User user) {
		this.user = user;		
	}

	public String getTitle() {
		return name;
	}

	public void setTitle(String title) {
		this.name = title.trim();
	}

	public String getUriName() {
		return uriName;
	}

	public void setUriName(String uriName) {
		this.uriName = ApplicationStore.removeAllSpaces(uriName.trim().toLowerCase());
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = ApplicationStore.removeNonAsciiChars(thumbnail.trim());
	}

	public String getDescription() {
		return description.trim();
	}

	public void setDescription(String description) {
		this.description = ApplicationStore.removeNonAsciiChars(description.trim());
	}

	public String getHtmlContent() {
		return htmlContent;
	}

	public void setHtmlContent(String htmlContent) {
		this.htmlContent = htmlContent;
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

	@Override
	public void setServletResponse(HttpServletResponse servletResponse) {
		this.servletResponse = servletResponse;
	}

	@Override
	public void setServletRequest(HttpServletRequest servletRequest) {
		this.servletRequest = servletRequest;
	}
}