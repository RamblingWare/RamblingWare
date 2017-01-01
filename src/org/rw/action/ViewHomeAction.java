package org.rw.action;

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
import org.rw.bean.Post;
import org.rw.bean.User;
import org.rw.bean.UserAware;
import org.rw.model.ApplicationStore;

import com.opensymphony.xwork2.ActionSupport;

/**
 * View Home action class
 * @author Austin Delamar
 * @date 11/30/2015
 */
public class ViewHomeAction extends ActionSupport implements UserAware, ServletResponseAware, ServletRequestAware {

	private static final long serialVersionUID = 1L;
	
	// results
	private ArrayList<Post> posts = new ArrayList<Post>();
	private ArrayList<Author> authors = new ArrayList<Author>();
	
	public String execute() {
		
		try{ /* Try to set UTF-8 page encoding */
			servletRequest.setCharacterEncoding("UTF-8");
		} catch(Exception e) {
			System.err.println("Failed to set UTF-8 request encoding.");
		}
		
		// /home
		
		// this shows 3 recent blog posts
		System.out.println("User has requested to view home.");
		Connection conn = null;
		try {
			conn = ApplicationStore.getConnection();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("select p.post_id, p.user_id, p.title, p.uri_name, p.is_visible, p.create_date, p.modify_date, p.thumbnail, p.banner, p.banner_caption, p.is_featured, p.description from posts p where is_visible <> 0 order by p.create_date desc limit 5");
			
			while(rs.next()) {
				
				// get the post properties
				int post_id = rs.getInt("post_id");
				String post_title = rs.getString("title");
				Date create_date = rs.getDate("create_date");
				String post_uri_name = rs.getString("uri_name");
				
				// save info into an object
				Post post = new Post(post_id,post_title,post_uri_name,null,create_date);
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
				posts.add(post);
			}
			
			// gather tags
			for(Post p : posts)
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
			servletRequest.setAttribute("posts", posts);
			
			// gather authors
			ResultSet rs2 = st.executeQuery("select a.user_id, a.name, a.uri_name, a.thumbnail, a.description, a.create_date, a.modify_date from users a order by a.create_date desc limit 3");
			
			while(rs2.next()) {
				
				// get the user properties
				int user_id = rs2.getInt("user_id");
				String name = rs2.getString("name");
				Date create_date = rs2.getDate("create_date");
				String uri_name = rs2.getString("uri_name");
				
				// save info into an object
				Author author = new Author(user_id,uri_name,name,create_date);
				author.setDescription(rs2.getString("description"));
				author.setModifyDate(rs2.getDate("modify_date"));
				author.setThumbnail(rs2.getString("thumbnail"));
				
				// add to results list
				authors.add(author);
			}
			
			servletRequest.setAttribute("authors", authors);
			
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

	public ArrayList<Post> getPosts() {
		return posts;
	}

	public void setPosts(ArrayList<Post> posts) {
		this.posts = posts;
	}

	public ArrayList<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(ArrayList<Author> authors) {
		this.authors = authors;
	}
}