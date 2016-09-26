package org.amd.action;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.amd.bean.ArchiveAware;
import org.amd.bean.Post;
import org.amd.bean.RecentViewAware;
import org.amd.bean.User;
import org.amd.bean.UserAware;
import org.amd.model.ApplicationStore;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

/**
 * View Home action class
 * @author Austin Delamar
 * @date 11/30/2015
 */
public class ViewHomeAction extends ActionSupport implements ArchiveAware, RecentViewAware, UserAware, ServletResponseAware, ServletRequestAware {

	private static final long serialVersionUID = 1L;
	
	// post parameters
	private String title;
	private String uriName;
	private String createDate;
	private String modifyDate;
	private String author;
	private boolean visible;
	private boolean featured;
	private String thumbnail;
	private String banner;
	private String bannerCaption;
	private String htmlContent;
	private String description;
	private ArrayList<String> tags;
	
	public String execute() {
		
		try{ /* Try to set UTF-8 page encoding */
			servletRequest.setCharacterEncoding("UTF-8");
		} catch(Exception e) {
			System.err.println("Failed to set UTF-8 request encoding.");
		}
		
		// this page shows the newest blog post

		// search in db for post by title
		Connection conn = null;
		try {
			conn = ApplicationStore.getConnection();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("select * from posts where is_visible <> 0 order by create_date desc limit 1");
			
			if(rs.first()) {
				// get the post properties
				int post_id = rs.getInt("post_id");
				title = rs.getString("title");
				uriName = rs.getString("uri_name");
				createDate = ApplicationStore.formatReadableDate(rs.getDate("create_date"));
				modifyDate = ApplicationStore.formatReadableDate(rs.getDate("modify_date"));
				author = "Austin Delamar";
				visible = rs.getInt("is_visible") > 0;
				featured = rs.getInt("is_featured") > 0;
				thumbnail= rs.getString("thumbnail");
				banner = rs.getString("banner");
				bannerCaption = rs.getString("banner_caption");
				htmlContent = rs.getString("html_content");
				description = rs.getString("description");
				
				ResultSet rs2 = st.executeQuery("select * from tags where post_id = "+post_id);
				
				// get post tags - there could be more than 1
				tags = new ArrayList<String>();
				while(rs2.next()) {
					tags.add(rs2.getString("tag_name"));
				}
			}
			
			// was post found AND is it publicly visible yet?
			if(title != null && isVisible())
			{
				// success
				System.out.println("User opened home: "+title);
				
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

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}
	
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
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

	public ArrayList<String> getTags() {
		return tags;
	}

	public void setTags(ArrayList<String> tags) {
		this.tags = tags;
	}

	@Override
	public void setArchive_years(ArrayList<String> archive_years) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setArchive_tags(ArrayList<String> archive_tags) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setRecent_view(ArrayList<Post> recent_view) {
		// TODO Auto-generated method stub
		
	}
}