package org.rw.action;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.rw.bean.Post;
import org.rw.bean.User;
import org.rw.bean.UserAware;
import org.rw.model.ApplicationStore;

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
	private ArrayList<Post> results = new ArrayList<Post>();
	
	public String execute() {
		
		try{ /* Try to set UTF-8 page encoding */
			servletRequest.setCharacterEncoding("UTF-8");
		} catch(Exception e) {
			System.err.println("Failed to set UTF-8 request encoding.");
		}
		
		// this page shows the RSS feed

		// search in db for all posts up to 100
		Connection conn = null;
		String response = "<?xml version=\"1.0\"?>"+
				"<rss version=\"2.0\">"+
				"<channel>"+
				"<title>RamblingWare Blog</title>"+
				"<description>This is my blog about computers, programming, tech, and things that bother me. I hope it bothers you too.</description>"+
				"<link>https://www.ramblingware.com</link>";
		try {
			conn = ApplicationStore.getConnection();
			Statement st = conn.createStatement();
			String searchQry = "select p.post_id, p.title, p.uri_name, p.publish_date, p.description from posts p where p.is_visible <> 0 order by p.create_date desc limit 10";
			System.out.println("QRY = "+searchQry);
			ResultSet rs = st.executeQuery(searchQry);
			
			while(rs.next()) {
				
				// get the post properties
				Post post = new Post(rs.getInt("post_id"));
				post.setTitle(rs.getString("title"));
				post.setUriName(rs.getString("uri_name"));
				//post.setCreateDate(rs.getDate("create_date"));
				post.setPublishDate(rs.getDate("publish_date"));
				//post.setAuthorId(rs.getInt("user_id"));
				post.setDescription(rs.getString("description"));
				//post.setHtmlContent(rs.getString("html_content"));
				//post.setIs_visible(rs.getInt("is_visible")==1);
				//post.setIsFeatured(rs.getInt("is_featured")==1);
				//post.setModifyDate(rs.getDate("modify_date"));
				//post.setThumbnail(rs.getString("thumbnail"));
				//post.setBanner(rs.getString("banner"));
				//post.setBannerCaption(rs.getString("banner_caption"));
				
				// add to results list
				results.add(post);
				
				response += "<item><title>"+post.getTitle()+"</title>"+
						"<description>"+post.getDescription()+"</description>"+
						"<pubDate>"+post.getPublishDateReadable()+"</pubDate>"+
						"<link>https://www.ramblingware.com/blog/post/"+post.getUriName()+"</link>"+
						"</item>";
			}
			response += "</channel></rss>";
			
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
}