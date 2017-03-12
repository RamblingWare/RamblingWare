package org.rw.action;

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
import org.rw.bean.Post;
import org.rw.bean.User;
import org.rw.bean.UserAware;
import org.rw.model.ApplicationStore;

import com.opensymphony.xwork2.ActionSupport;

/**
 * View Blog list action class
 * @author Austin Delamar
 * @date 11/9/2015
 */
public class ViewBlogAction extends ActionSupport implements UserAware, ServletResponseAware, ServletRequestAware {

	private static final long serialVersionUID = 1L;
	
	// results
	private ArrayList<Post> results = new ArrayList<Post>();
	
	public String execute() {
		
		try{ /* Try to set UTF-8 page encoding */
			servletRequest.setCharacterEncoding("UTF-8");
		} catch(Exception e) {
			System.err.println("Failed to set UTF-8 request encoding.");
		}
		
		// /blog
		
		// this shows 7 recent blog posts
		System.out.println("User has requested to view the Blog");
		Connection conn = null;
		try {
			conn = ApplicationStore.getConnection();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("select p.* from posts p where is_visible <> 0 order by p.create_date desc limit 10");
			
			while(rs.next()) {
				
				// get the post properties
				Post post = new Post(rs.getInt("post_id"));
				post.setTitle(rs.getString("title"));
				post.setUriName(rs.getString("uri_name"));
				post.setCreateDate(rs.getDate("create_date"));
				post.setPublishDate(rs.getDate("publish_date"));
				post.setAuthorId(rs.getInt("user_id"));
				post.setDescription(rs.getString("description"));
				//post.setHtmlContent(rs.getString("html_content"));
				post.setIs_visible(rs.getInt("is_visible")==1);
				post.setIsFeatured(rs.getInt("is_featured")==1);
				post.setModifyDate(rs.getDate("modify_date"));
				post.setThumbnail(rs.getString("thumbnail"));
				post.setBanner(rs.getString("banner"));
				post.setBannerCaption(rs.getString("banner_caption"));
				
				// add to results list
				results.add(post);
			}
			
			// gather tags
			//System.out.println("Gathering tags...");
			for(Post p : results)
			{
				ResultSet rs2 = st.executeQuery("select * from tags where post_id = "+p.getId());
				
				// get this post's tags - there could be more than 1
				ArrayList<String> post_tags = new ArrayList<String>();
				while(rs2.next()) {
					post_tags.add(rs2.getString("tag_name"));
				}
				p.setTags(post_tags);
				
				ResultSet rs3 = st.executeQuery("select name, uri_name from users where user_id = "+p.getAuthorId());
				if(rs3.next())
				{
					p.setAuthor(rs3.getString("name"));
					p.setUriAuthor(rs3.getString("uri_name"));
				}
				else
				{
					p.setAuthor("Anonymous");
					p.setUriAuthor("anonymous");
				}
			}
			
			//System.out.println(results.size()+" result(s) found.");
			
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
		// TODO Auto-generated method stub
		
	}

	public ArrayList<Post> getResults() {
		return results;
	}

	public void setResults(ArrayList<Post> results) {
		this.results = results;
	}
}