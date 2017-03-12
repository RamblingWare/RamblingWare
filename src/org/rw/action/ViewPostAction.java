package org.rw.action;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.rw.bean.User;
import org.rw.bean.UserAware;
import org.rw.model.ApplicationStore;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

/**
 * View Post action class
 * @author Austin Delamar
 * @date 11/9/2015
 */
public class ViewPostAction extends ActionSupport implements UserAware, ServletResponseAware, ServletRequestAware {

	private static final long serialVersionUID = 1L;
	private User user;
	
	// post parameters
	private String title;
	private String uriName;
	private String createDate;
	private String modifyDate;
	private String publishDate;
	private boolean visible;
	private boolean featured;
	private String thumbnail;
	private String banner;
	private String bannerCaption;
	private String htmlContent;
	private String description;
	private ArrayList<String> tags;
	
	private String author;
	private String authorUri;
	private String authorThumbnail;
	private String authorDesc;
	
	public String execute() {
		
		try{ /* Try to set UTF-8 page encoding */
			servletRequest.setCharacterEncoding("UTF-8");
		} catch(Exception e) {
			System.err.println("Failed to set UTF-8 request encoding.");
		}
		
		// /post/file-name-goes-here
		// this allows blog posts to be shown without parameter arguments (i.e. ?uri_name=foobar&test=123 )
		String  uriTemp = servletRequest.getRequestURI().toLowerCase();
		if(uriName == null && uriTemp.startsWith("/blog/post/"))
			uriName = ApplicationStore.removeBadChars(uriTemp.substring(11,uriTemp.length()));
		else if(uriName == null && uriTemp.startsWith("/manage/viewpost/"))
			uriName = ApplicationStore.removeBadChars(uriTemp.substring(17,uriTemp.length()));
		
		//System.out.println("URI: "+uri+" (ViewPost = "+uri_name+")");
		
		if(uriName != null && uriName.length() > 0)
		{
			// search in db for post by title
			Connection conn = null;
			try {
				conn = ApplicationStore.getConnection();
				Statement st = conn.createStatement();
				
				ResultSet rs = st.executeQuery("select p.*, u.name, u.uri_name as 'authorUri', u.description as 'authorDesc', u.thumbnail as 'authorThumbnail' "+
						"from posts p "+
						"left join users u on p.user_id = u.user_id "+
						"where p.uri_name = '"+uriName+"'");
				
				if(rs.next()) {
					// get the post properties
					int post_id = rs.getInt("post_id");
					title = rs.getString("title");
					createDate = ApplicationStore.formatReadableDate(rs.getDate("create_date"));
					modifyDate = ApplicationStore.formatReadableDate(rs.getDate("modify_date"));
					publishDate = ApplicationStore.formatReadableDate(rs.getDate("publish_date"));
					visible = rs.getInt("is_visible") > 0;
					featured = rs.getInt("is_featured") > 0;
					thumbnail= rs.getString("thumbnail");
					banner = rs.getString("banner");
					bannerCaption = rs.getString("banner_caption");
					htmlContent = rs.getString("html_content");
					description = rs.getString("description");
					
					author = rs.getString("name");
					authorUri = rs.getString("authorUri");
					authorThumbnail = rs.getString("authorThumbnail");
					authorDesc = rs.getString("authorDesc");
					
					ResultSet rs2 = st.executeQuery("select * from tags where post_id = "+post_id);
					
					// get post tags - there could be more than 1
					tags = new ArrayList<String>();
					while(rs2.next()) {
						tags.add(rs2.getString("tag_name"));
					}
				}
				
				
				
				// was post found AND is it publicly visible yet?
				if(title != null && (isVisible() || user!=null))
				{
					// yes, it is.
					System.out.println("User opened post: "+uriName);
					
					// set attributes
					servletRequest.setAttribute("title", title);
					servletRequest.setAttribute("uriName", uriName);
					servletRequest.setAttribute("author", author);
					servletRequest.setAttribute("thumbnail", thumbnail);
					servletRequest.setAttribute("banner", banner);
					servletRequest.setAttribute("bannerCaption", bannerCaption);
					servletRequest.setAttribute("htmlContent", htmlContent);
					servletRequest.setAttribute("description", description);
					servletRequest.setAttribute("tags", tags);
					servletRequest.setAttribute("createDate", createDate);
					servletRequest.setAttribute("modifyDate", modifyDate);
					
					// Remember the most recently viewed articles/posts using cookies
					Cookie ck = getCookie("recent-view");
					if(ck != null && !ck.getValue().isEmpty())
					{
						// Put them into a hashset so we don't have duplicates
						HashSet<String> hs = new HashSet<String>();
						String[] ckv = ck.getValue().split("\\|");
						for(String uri : ckv)
						{
							hs.add(uri);
						}
						hs.add(uriName);
						ck.setValue("");
						ckv = (String[]) hs.toArray(new String[hs.size()]);
						for(String uri : ckv)
						{
							ck.setValue(ck.getValue()+"|"+uri);
						}
						setCookie("recent-view",ck.getValue());
					}
					else
						setCookie("recent-view",uriName);
					
					// forward to appropriate JSP page
					//servletRequest.getRequestDispatcher("/WEB-INF/post/post.jsp").forward(servletRequest, servletResponse);
					
					return Action.SUCCESS;
				}
				else
				{
					System.out.println("User tried to open post: "+uriName+" ("+isVisible()+")");
					addActionError("Post '"+uriName+"' not found. Please try again.");
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
			addActionError("Post '"+uriName+"' not found. Please try again.");
			return Action.NONE;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUriName() {
		return uriName;
	}

	public void setUriName(String uriName) {
		this.uriName = uriName;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String date) {
		this.createDate = date;
	}

	public ArrayList<String> getTags() {
		return tags;
	}

	public void setTags(ArrayList<String> tags) {
		this.tags = tags;
	}

	public String getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
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

	public String getHtmlContent() {
		return htmlContent;
	}

	public void setHtmlContent(String htmlContent) {
		this.htmlContent = htmlContent;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getAuthorUri() {
		return authorUri;
	}

	public void setAuthorUri(String authorUri) {
		this.authorUri = authorUri;
	}

	public String getAuthorThumbnail() {
		return authorThumbnail;
	}

	public void setAuthorThumbnail(String authorThumbnail) {
		this.authorThumbnail = authorThumbnail;
	}

	public String getAuthorDesc() {
		return authorDesc;
	}

	public void setAuthorDesc(String authorDesc) {
		this.authorDesc = authorDesc;
	}

	public boolean isFeatured() {
		return featured;
	}

	public void setFeatured(boolean featured) {
		this.featured = featured;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public String getBanner() {
		return banner;
	}

	public void setBanner(String banner) {
		this.banner = banner;
	}

	public String getBannerCaption() {
		return bannerCaption;
	}

	public void setBannerCaption(String bannerCaption) {
		this.bannerCaption = bannerCaption;
	}
}