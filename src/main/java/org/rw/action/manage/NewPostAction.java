package org.rw.action.manage;

import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.rw.action.model.Author;
import org.rw.action.model.Post;
import org.rw.action.model.UserAware;
import org.rw.config.Application;
import org.rw.config.Utils;

import com.opensymphony.xwork2.ActionSupport;
 
/**
 * New Post action class
 * @author Austin Delamar
 * @date 5/30/2016
 */
public class NewPostAction extends ActionSupport implements UserAware, ServletResponseAware, ServletRequestAware{
	 
    private static final long serialVersionUID = 1L;
    private Author user;
    
    private String title;
    private String uriName;
    private String thumbnail;
    private String publishDate;
    
    private boolean visible;
    private boolean featured;
    
    private boolean hasBanner;
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
    	
    	if(servletRequest.getParameter("submitForm")!=null)
		{
			// they've submitted a new post!
    		
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
			if(hasBanner && (banner == null || banner.trim().isEmpty()))
			{
				addActionError("Banner Image was empty. Please fill out all fields before saving.");
				System.out.println(user.getUsername()+" failed to edit post. Banner was empty.");
				return ERROR;
			}
    		if(tags == null || tags.trim().isEmpty())
    		{
    			tags = "none";
    		}
    		

			// check that the URI is unique
			try {
				Post post = Application.getDatabaseSource().getPost(uriName, true);
				
				if(post != null)
				{
					// URI was not unique. Please try again.
					addActionError("URI is not unique. Its being used by another post. Please change it, and try again.");
					System.out.println("URI was not unique.");
					return ERROR;
				}
				
				// save fields into object
				post = new Post(-1);
				post.setUriName(uriName);
				post.setTitle(title);
				post.setAuthor(user);
				Calendar cal = Calendar.getInstance();
				cal.setTime(Utils.convertStringToDate(publishDate));
				post.setPublishDate(new java.sql.Date(cal.getTimeInMillis()));
				post.setVisible(visible);
				post.setFeatured(featured);
				post.setBanner(banner);
				post.setBannerCaption(bannerCaption);
				post.setThumbnail(thumbnail);
				post.setDescription(description);
				post.setHtmlContent(htmlContent);
				
				// chop off [ ] if they added them
				tags = tags.replaceAll("\\[", "");
				tags = tags.replaceAll("\\]", "");
				System.out.println("Tags: '"+tags+"'");
				
				String[] tagsArray = tags.split(",");
				ArrayList<String> tagsList = new ArrayList<String>();
				for(String t : tagsArray) {
					if(!t.trim().isEmpty())
						tagsList.add(t.trim());
				}
				post.setTags(tagsList);
				
				// insert into database
				post = Application.getDatabaseSource().newPost(post);
				
				if(post.getId() != -1)
				{
					// Success
					System.out.println("User "+user.getUsername()+" submitted a new post: "+uriName);
					addActionMessage("Successfully created new post.");
					return "edit";
				}
				else {
					// failed to insert
					addActionError("Oops. Failed to create new post. Please try again.");
					System.out.println("Failed to create new post. "+uriName);
					return ERROR;
				}
				
			} catch (Exception e) {
				addActionError("An error occurred: "+e.getMessage());
				e.printStackTrace();
				return ERROR;
			}
		}
    	
    	// they opened the form
		System.out.println("User "+user.getUsername()+" opened new post page.");
		return SUCCESS;
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
		this.uriName = Utils.removeAllSpaces(uriName.trim().toLowerCase());
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = Utils.removeNonAsciiChars(thumbnail.trim());
	}

	public String getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public boolean isFeatured() {
		return featured;
	}

	public void setFeatured(boolean featured) {
		this.featured = featured;
	}

	public boolean getHasBanner() {
		return hasBanner;
	}

	public void setHasBanner(boolean hasBanner) {
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
		this.tags = Utils.removeNonAsciiChars(tags.trim());
	}

	public String getDescription() {
		return description.trim();
	}

	public void setDescription(String description) {
		this.description = Utils.removeNonAsciiChars(description.trim());
	}

	public String getHtmlContent() {
		return htmlContent;
	}

	public void setHtmlContent(String htmlContent) {
		this.htmlContent = htmlContent;
	}

	@Override
	public void setUser(Author user) {
		this.user = user;
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