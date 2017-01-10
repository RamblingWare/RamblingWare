package org.rw.action.manage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Edit Author action class
 * @author Austin Delamar
 * @date 10/23/2016
 */
public class EditUserAction extends ActionSupport implements UserAware, ServletResponseAware, ServletRequestAware {

	private static final long serialVersionUID = 1L;
	private User user;
	
	// post parameters
	private Author author;
	private int id;
	private String uri;
	
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
		
		// /manage/editpost/user-name-goes-here
		// this allows blog posts to be shown without parameter arguments (i.e. ?uri=foobar&test=123 )
		String  uriTemp = servletRequest.getRequestURI();
		if(uri == null && uriTemp.startsWith("/manage/edituser/"))
			uri = ApplicationStore.removeBadChars(uriTemp.substring(17,uriTemp.length()));
		
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
						+ "html_content = ? where user_id = "+id;
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
			
			System.out.println("User "+user.getUsername()+" updated user: "+uri);
			addActionMessage("Successfully saved changes to the author.");
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
				
				int r = st.executeUpdate("delete from users where user_id = "+id);
				
				if(r == 0) {
					conn.rollback();
					addActionError("Oops. Failed to delete the user. Please try again.");
					System.out.println("Failed to delete user: "+id);
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
			
			System.out.println("User "+user.getUsername()+" deleted user: "+uri);
			addActionMessage("The author was deleted!");
			return "edit";
		}
		else
		{
			//System.out.println("URI: "+uri+" (ViewPost = "+uri+")");
			
			if(uri != null && uri.length() > 0)
			{
				// search in db for user by name
				Connection conn = null;
				try {
					conn = ApplicationStore.getConnection();
					Statement st = conn.createStatement();
					ResultSet rs = st.executeQuery("select * from users where uri_name= '"+uri+"'");
					
					if(rs.first() && uri.equals(rs.getString("uri_name"))) {
						// get the author properties
						int user_id = rs.getInt("user_id");
						name = rs.getString("name");
						email = rs.getString("email");
						Date createDate = rs.getDate("create_date");
						
						// save info into an object
						Author auth = new Author(user_id,uri,name,createDate);
						auth.setEmail(email);
						auth.setId(rs.getInt("user_id"));
						auth.setDescription(rs.getString("description"));
						auth.setHtmlContent(rs.getString("html_content"));
						auth.setModifyDate(rs.getDate("modify_date"));
						auth.setThumbnail(rs.getString("thumbnail"));
						
						author = auth;
						
						System.out.println("User "+user.getUsername()+" opened user to edit: "+uri);
						
						// set attributes
						servletRequest.setAttribute("author", author);
						
						return Action.SUCCESS;
					}
					else
					{
						System.out.println("User "+user.getUsername()+" tried to edit user: "+uri);
						addActionError("User '"+uri+"' not found. Please try again.");
						return Action.NONE;
					}
				
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
			else
			{
				addActionError("User '"+uri+"' not found. Please try again.");
				return Action.NONE;
			}
		}
	}
	
	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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