package org.rw.action.manage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.rw.bean.Post;
import org.rw.bean.User;
import org.rw.bean.UserAware;
import org.rw.model.ApplicationStore;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Edit Post action class
 * @author Austin Delamar
 * @date 5/30/2016
 */
public class EditPostAction extends ActionSupport implements UserAware, ServletResponseAware, ServletRequestAware {

	private static final long serialVersionUID = 1L;
	private User user;
	
	// post parameters
	private Post post;
	private int id;
	private String uri;
	
	// updated values
	private String title;
    private String uriName;
    private String thumbnail;
    private String publishDate;
    
    private Boolean isVisible;
    private Boolean isFeatured;
    
    private Boolean hasBanner;
    private String banner;
    private String bannerCaption;
    
    private String tags;
    private String description;
    private String htmlContent;
	
	public String execute() {
		
		try{ /* Try to set UTF-8 page encoding */
			servletRequest.setCharacterEncoding("UTF-8");
		} catch(Exception e) {
			System.err.println("Failed to set UTF-8 request encoding.");
		}
		
		// /manage/editpost/file-name-goes-here
		// this allows blog posts to be shown without parameter arguments (i.e. ?uri=foobar&test=123 )
		String  uriTemp = servletRequest.getRequestURI().toLowerCase();
		if(uri == null && uriTemp.startsWith("/manage/editpost/"))
			uri = ApplicationStore.removeBadChars(uriTemp.substring(17,uriTemp.length()));
		
		if(servletRequest.getParameter("submit")!=null)
		{
			// Validate each field
    		if(title == null || title.trim().isEmpty())
    		{
    			addActionError("Title was empty. Please fill out all fields before saving.");
    			System.out.println(user.getUsername()+" failed to edit post. Title was empty.");
    			return ERROR;
    		}
    		if(uriName == null || uriName.trim().isEmpty())
    		{
    			addActionError("URI Name was empty. Please fill out all fields before saving.");
    			System.out.println(user.getUsername()+" failed to edit post. URI was empty.");
    			return ERROR;
    		}
    		if(thumbnail == null || thumbnail.trim().isEmpty())
    		{
    			addActionError("Thumbnail was empty. Please fill out all fields before saving.");
    			System.out.println(user.getUsername()+" failed to edit post. Thumbnail was empty.");
    			return ERROR;
    		}
    		if(description == null || description.trim().isEmpty())
    		{
    			addActionError("Description was empty. Please fill out all fields before saving.");
    			System.out.println(user.getUsername()+" failed to edit post. Description was empty.");
    			return ERROR;
    		}
    		if(htmlContent == null || htmlContent.trim().isEmpty())
    		{
    			addActionError("Post Content was empty. Please fill out all fields before saving.");
    			System.out.println(user.getUsername()+" failed to edit post. Content was empty.");
    			return ERROR;
    		}
			if(htmlContent.length() > 12288)
			{
				addActionError("Post Content is too long. Character limit is 12,288. Please shorten the post.");
				System.out.println(user.getUsername()+" failed to edit post. Content too large.");
				return ERROR;
			}
			if(hasBanner!=null && (banner == null || banner.trim().isEmpty()))
			{
				addActionError("Banner Image was empty. Please fill out all fields before saving.");
				System.out.println(user.getUsername()+" failed to edit post. Banner was empty.");
				return ERROR;
			}
    		if(tags == null || tags.trim().isEmpty())
    		{
    			tags = "none";
    		}
    		

			// they've submitted an edit on a post
			Connection conn = null;
			Statement st = null;
			try {
				conn = ApplicationStore.getConnection();
				st = conn.createStatement();
				conn.setAutoCommit(false);
				
				String update = "update posts set "
						+ "user_id = ?,"
						+ "title = ?,"
						+ "uri_name = ?,"
						+ "modify_date = CURRENT_TIMESTAMP,"
						+ "publish_date = ?,"
						+ "is_visible = ?,"
						+ "is_featured = ?,"
						+ "thumbnail = ?,"
						+ "description = ?,"
						+ "banner = ?,"
						+ "banner_caption = ?,"
						+ "html_content = ? where post_id = "+id;
				PreparedStatement pt = conn.prepareStatement(update);
				pt.setString(1, user.getUserId());
				pt.setString(2, title);
				pt.setString(3, uriName);
				Calendar cal = Calendar.getInstance();
				cal.setTime(ApplicationStore.convertStringToDate(publishDate));
				pt.setDate(4, new java.sql.Date(cal.getTimeInMillis()));
				pt.setInt(5, isVisible!=null?1:0);
				pt.setInt(6, isFeatured!=null?1:0);
				pt.setString(7, thumbnail);
				pt.setString(8, description);
				pt.setString(9, banner);
				pt.setString(10, bannerCaption);
				pt.setString(11, htmlContent);
				
				if(pt.execute())
				{
					// failed to update post
					conn.rollback();
					addActionError("Oops. Failed to update the post. Please try again.");
					System.out.println("Failed to update the post. "+uriName);
					return ERROR;
				}
				
				// remove old tags
				int rt = st.executeUpdate("delete from tags where post_id = "+id);
				
				if(rt == 0) {
					conn.rollback();
					addActionError("Oops. Failed to delete the post tags. Please try again.");
					System.out.println("Failed to delete post tags: "+id);
					return ERROR;
				}
				
				// insert new tags
				
				// chop off [ ] if they added them
				tags = tags.replaceAll("\\[", "");
				tags = tags.replaceAll("\\]", "");
				System.out.println("Tags: '"+tags+"'");
				
				String[] tagsArray = tags.split(",");
				
				String qry = "insert into tags (post_id,tag_name) values ";
				for(String t : tagsArray) {
					if(!t.trim().isEmpty())
						qry+="("+id+",'"+t.trim()+"'),";
				}
				qry = qry.substring(0, qry.length()-1); // remove last comma
				
				// insert tags
				int r = st.executeUpdate(qry);
				
				if(r == 0) {
					// error inserting tags
					conn.rollback();
					addActionError("Oops. Failed to add tags to the post. Please try adding them later.");
					System.out.println("Failed to add tags to the post: "+uriName);
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
			
			// successfully updated
			System.out.println("User "+user.getUsername()+" saved changes to the post: "+uri);
			addActionMessage("Successfully saved changes to the post.");
			return "edit";
		}
		else if(servletRequest.getParameter("delete")!=null)
		{
			// they've requested to delete a post
			Connection conn = null;
			Statement st = null;
			try {
				conn = ApplicationStore.getConnection();
				st = conn.createStatement();
				conn.setAutoCommit(false);
				
				int r = st.executeUpdate("delete from posts where post_id = "+id);
				
				if(r == 0) {
					conn.rollback();
					addActionError("Oops. Failed to delete the post. Please try again.");
					System.out.println("Failed to delete post: "+id);
					return ERROR;
				}
				
				int t = st.executeUpdate("delete from tags where post_id = "+id);
				
				if(t == 0) {
					conn.rollback();
					addActionError("Oops. Failed to delete the post tags. Please try again.");
					System.out.println("Failed to delete post tags: "+id);
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
			
			System.out.println("User "+user.getUsername()+" deleted post: "+uri);
			addActionMessage("The post was deleted!");
			return "edit";
		}
		else
		{
			//System.out.println("URI: "+uri+" (ViewPost = "+uri+")");
			
			if(uri != null && uri.length() > 0)
			{
				// search in db for post by title
				Connection conn = null;
				try {
					conn = ApplicationStore.getConnection();
					Statement st = conn.createStatement();
					ResultSet rs = st.executeQuery("select * from posts where uri_name= '"+uri+"'");
					
					if(rs.first() && uri.equals(rs.getString("uri_name"))) {
						
						// get the post properties
						Post post = new Post(rs.getInt("post_id"));
						post.setTitle(rs.getString("title"));
						post.setUriName(rs.getString("uri_name"));
						post.setCreateDate(rs.getDate("create_date"));
						post.setPublishDate(rs.getDate("publish_date"));
						post.setAuthorId(rs.getInt("user_id"));
						post.setDescription(rs.getString("description"));
						post.setHtmlContent(rs.getString("html_content"));
						post.setIs_visible(rs.getInt("is_visible")==1);
						post.setIsFeatured(rs.getInt("is_featured")==1);
						post.setModifyDate(rs.getDate("modify_date"));
						post.setThumbnail(rs.getString("thumbnail"));
						post.setBanner(rs.getString("banner"));
						post.setBannerCaption(rs.getString("banner_caption"));						
						
						ResultSet rs2 = st.executeQuery("select * from tags where post_id = "+post.getId());
						
						// get this post's tags - there could be more than 1
						ArrayList<String> post_tags = new ArrayList<String>();
						while(rs2.next()) {
							post_tags.add(rs2.getString("tag_name"));
						}
						post.setTags(post_tags);
						
						ResultSet rs3 = st.executeQuery("select name, uri_name from users where user_id = "+post.getAuthorId());
						if(rs3.next())
						{
							post.setAuthor(rs3.getString("name"));
							post.setUriAuthor(rs3.getString("uri_name"));
						}
						else
						{
							post.setAuthor("Anonymous");
							post.setUriAuthor("anonymous");
						}
						
						
						System.out.println("User "+user.getUsername()+" opened post to edit: "+uri);
						
						// set attributes
						servletRequest.setAttribute("post", post);
						
						return Action.SUCCESS;
					}
					else
					{
						System.out.println("User "+user.getUsername()+" tried to edit post: "+uri);
						addActionError("Post '"+uri+"' not found. Please try again.");
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
				addActionError("Post '"+uri+"' not found. Please try again.");
				return Action.NONE;
			}
		}
	}
	
	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
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
		return title;
	}

	public void setTitle(String title) {
		this.title = title.trim();
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

	public String getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}

	public Boolean getIsVisible() {
		return isVisible;
	}

	public void setIsVisible(Boolean isVisible) {
		this.isVisible = isVisible;
	}

	public Boolean getIsFeatured() {
		return isFeatured;
	}

	public void setIsFeatured(Boolean isFeatured) {
		this.isFeatured = isFeatured;
	}

	public Boolean getHasBanner() {
		return hasBanner;
	}

	public void setHasBanner(Boolean hasBanner) {
		this.hasBanner = hasBanner;
	}

	public String getBanner() {
		return banner;
	}

	public void setBanner(String banner) {
		this.banner = banner.trim();
	}

	public String getBannerCaption() {
		return bannerCaption;
	}

	public void setBannerCaption(String bannerCaption) {
		this.bannerCaption = bannerCaption;
	}

	public String getTags() {
		return tags.trim();
	}

	public void setTags(String tags) {
		this.tags = ApplicationStore.removeNonAsciiChars(tags.trim());
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