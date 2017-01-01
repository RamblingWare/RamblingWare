package org.rw.action.manage;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
 * New Post action class
 * @author Austin Delamar
 * @date 5/30/2016
 */
public class NewPostAction extends ActionSupport implements UserAware, ServletResponseAware, ServletRequestAware{
	 
    private static final long serialVersionUID = 1L;
    private User user;
    
    private String title;
    private String uriName;
    private String thumbnail;
    
    private Boolean isVisible;
    private Boolean isFeatured;
    
    private Boolean hasBanner;
    private String banner;
    private String bannerCaption;
    
    private String tags;
    private String description;
    private String htmlContent;
    
    @Override
    public String execute(){
    	
    	try{ /* Try to set UTF-8 page encoding */
			servletRequest.setCharacterEncoding("UTF-8");
		} catch(Exception e) {
			System.err.println("Failed to set UTF-8 request encoding.");
		}
    	
    	if(servletRequest.getParameter("submit")!=null)
		{
			// they've submitted a new post!
    		
    		// make sure nothing is empty
    		if(!title.isEmpty() && !uriName.isEmpty() && !description.isEmpty() && !htmlContent.isEmpty() && !tags.isEmpty())
    		{
    			// check that the URI is unique
        		Connection conn = null;
        		Statement st = null;
				try {
					conn = ApplicationStore.getConnection();
					st = conn.createStatement();
					conn.setAutoCommit(false);
					ResultSet rs = st.executeQuery("select COUNT(*) from posts where uri_name = '"+uriName+"'");
					
					if(rs.next() && rs.getInt(1) > 0)
					{
						// URI was not unique. Please try again.
						conn.rollback();
						addActionError("URI is not unique. Its being used by another post. Please change it, and try again.");
						System.out.println("URI was not unique.");
						return ERROR;
					}
					
					// save fields into database
					PreparedStatement pt = conn.prepareStatement(
							"insert into posts (user_id,title,uri_name,create_date,is_visible,is_featured,thumbnail,banner,banner_caption,description,html_content) values (?,?,?,?,?,?,?,?,?,?,?)");
					pt.setString(1, user.getUserId());
					pt.setString(2, title);
					pt.setString(3, uriName);
					pt.setDate(4, new Date(System.currentTimeMillis()));
					pt.setInt(5, isVisible!=null?1:0);
					pt.setInt(6, isFeatured!=null?1:0);
					pt.setString(7, thumbnail);
					pt.setString(8, banner);
					pt.setString(9, bannerCaption);
					pt.setString(10, description);
					pt.setString(11, htmlContent);
					
					if(pt.execute())
					{
						// failed to insert
						conn.rollback();
						addActionError("Oops. Failed to create new post. Please try again.");
						System.out.println("Failed to create new post. "+uriName);
						return ERROR;
					}
					
					// get the post_id and add the tags
					ResultSet rs1 = st.executeQuery("select post_id from posts where uri_name = '"+uriName+"'");
					rs1.next();
					int post_id = rs1.getInt(1);
					System.out.println("Tags: "+tags);
					
					String tempTags = tags;
					
					// chop off [ ] if they added them
					if(tags.startsWith("["))
						tempTags = tempTags.substring(1,tags.length());
					if(tags.endsWith("]"))
						tempTags = tempTags.substring(0,tags.length()-1);
					
					//System.out.println("TempTags: "+tempTags);
					String[] tagsArray = tempTags.split(",");
					
					String qry = "insert into tags (post_id,tag_name) values ";
					for(String t : tagsArray) {
						if(!t.trim().isEmpty())
							qry+="("+post_id+",'"+t.trim()+"'),";
					}
					qry = qry.substring(0, qry.length()-1); // remove last comma
					
					// insert tags
					int r = st.executeUpdate(qry);
					
					if(r == 0) {
						// error inserting tags
						conn.rollback();
						addActionError("Oops. Failed to add tags to the new post. Please try adding them later.");
						System.out.println("Failed to add tags to the new post: "+uriName);
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
        		
    			System.out.println("User "+user.getUsername()+" submitted a new post: "+uriName);
    			addActionMessage("Your post was created!");
    			return "edit";
    		}
    		else
    		{
    			// something was empty
    			addActionError("One or more fields were empty. Please fill out all fields before saving.");
    			return ERROR;
    		}
		}
    	else
    	{
    		// they opened the form
    		System.out.println("User "+user.getUsername()+" opened new post page.");
    		return SUCCESS;
    	}
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
		this.bannerCaption = ApplicationStore.removeNonAsciiChars(bannerCaption.trim());
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

	@Override
	public void setUser(User user) {
		this.user = user;
	}
	
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
     
}