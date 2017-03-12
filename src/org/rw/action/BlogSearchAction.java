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
 * Blog Search action class
 * @author Austin Delamar
 * @date 11/25/2015
 */
public class BlogSearchAction extends ActionSupport implements UserAware, ServletResponseAware, ServletRequestAware {

	private static final long serialVersionUID = 1L;
	
	// selection options
	private ArrayList<String> tagOptions = null;
	private ArrayList<Author> authorOptions = null;
	
	// search parameters
	private String title;
	private String tag;
	private String date;
	private String year;
	private String author;
	private String month;
	
	// search results
	private ArrayList<Post> results = new ArrayList<Post>();
	
	public String execute() {
		
		try{ /* Try to set UTF-8 page encoding */
			servletRequest.setCharacterEncoding("UTF-8");
		} catch(Exception e) {
			System.err.println("Failed to set UTF-8 request encoding.");
		}
		
		// gather options
		Connection conn = null;
		Statement st = null;
		try {
			conn = ApplicationStore.getConnection();
			st = conn.createStatement();
			
			// get author list
			ResultSet rs = st.executeQuery("select user_id, name, uri_name from users;");
			authorOptions = new ArrayList<Author>();
			while(rs.next())
			{
				// get the user properties
				Author author = new Author(rs.getInt("user_id"));
				author.setUriName(rs.getString("uri_name"));
				author.setName(rs.getString("name"));
				
				authorOptions.add(author);
			}
			rs.close();
			
			// get tag list
			ResultSet rs2 = st.executeQuery("select distinct t.tag_name from tags t inner join posts p on t.post_id = p.post_id where p.is_visible <> 0;");
			tagOptions = new ArrayList<String>();
			while(rs2.next()) {
				tagOptions.add(rs2.getString("tag_name"));
			}
			rs2.close();
			
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
		
		// /blog/search?title=
		// /blog/search?tag=
		// /blog/search?date=
		
		// search by one of the parameters provided
		String searchQry = "select p.post_id, p.title, p.uri_name, p.thumbnail, p.is_visible, p.create_date, p.modify_date, p.publish_date, p.description from posts p ";
		boolean parameterGiven = false;
		if(tag != null && !tag.isEmpty())
		{
			System.out.println("Search by tag = '"+tag+"'");
			servletRequest.setAttribute("tag", tag);
			searchQry += " inner join tags t on p.post_id = t.post_id where ";
			String tags[] = tag.split(" ");
			
			if(tags.length > 1)
			{
				for(String t : tags) {
					searchQry += "t.tag_name = '"+t+"' OR ";
				}
				searchQry = searchQry.substring(0, searchQry.length()-4);
			}
			else
			{
				searchQry += "t.tag_name = '"+tag+"' ";
			}
			parameterGiven = true;
		}
		else tag  = "";
		
		if(title != null && !title.isEmpty())
		{
			System.out.println("Search by uri_name like '%"+title+"%'");
			servletRequest.setAttribute("title", title);
			if(parameterGiven)
				searchQry += "and";
			else
				searchQry += "where";
			searchQry += " p.uri_name like '%"+title+"%' ";
			parameterGiven = true;
		}
		else title = "";
		
		if( (date != null && !date.isEmpty()) ||
			(month != null && !month.isEmpty()) ||
			(year != null && !year.isEmpty()) )
		{
			if(date !=null && !date.isEmpty() && (month == null || month.isEmpty()))
				month = date.substring(0,date.indexOf(' ')).trim();
			
			if(date !=null && !date.isEmpty() && (year == null || year.isEmpty()))
				year = date.substring(date.indexOf(' ')).trim();
			
			
			int mth = -1;
			Date beginDate = null;
			Date endDate = null;
			
			if(month != null && !month.isEmpty())
			{
				switch(month) {
					case "Jan": mth = 1; break;
					case "Feb": mth = 2; break;
					case "Mar": mth = 3; break;
					case "Apr": mth = 4; break;
					case "May": mth = 5; break;
					case "Jun": mth = 6; break;
					case "Jul": mth = 7; break;
					case "Aug": mth = 8; break;
					case "Sep": mth = 9; break;
					case "Oct": mth = 10; break;
					case "Nov": mth = 11; break;
					case "Dec": mth = 12; break;
				}
				beginDate = ApplicationStore.convertStringToDate(mth+"/1/"+year);
				endDate = ApplicationStore.convertStringToDate((mth+1)+"/1/"+year);
			}
			else
			{
				beginDate = ApplicationStore.convertStringToDate("1/1/"+year);
				endDate = ApplicationStore.convertStringToDate("12/31/"+year);
			}
			
			//System.out.println("month = "+month);
			//System.out.println("mth = "+mth);
			//System.out.println("year = "+year);
			
			
			if(beginDate == null || endDate == null)
			{
				//System.out.println("beginDate = "+beginDate);
				//System.out.println("endDate = "+endDate);
				System.out.println("Search by date was not recognized. Please try again.");
				addActionError("Search by date was not recognized. Please try again.");
				return ERROR;
			}
			System.out.println("Search by date = '"+date+"' / month '"+month+"' year '"+year+"' ");
			servletRequest.setAttribute("date", date);
			servletRequest.setAttribute("year", year);
			servletRequest.setAttribute("month", month);
			if(parameterGiven)
				searchQry += "and";
			else
				searchQry += "where";
			searchQry += " p.publish_date >= '"+ApplicationStore.formatMySQLDate(beginDate)+" 00:00:00' and p.publish_date <= '"+ApplicationStore.formatMySQLDate(endDate)+" 00:00:00' ";
			parameterGiven = true;
		}
		
		// was no parameter given?
		if(!parameterGiven)
		{
			return SUCCESS;
		}
		
		// search in db for post by title(uri_name), tag, and/or date
		Connection conn2 = null;
		Statement st2 = null;
		try {
			conn2 = ApplicationStore.getConnection();
			st2 = conn2.createStatement();
			searchQry += " AND is_visible <> 0 order by p.create_date desc limit 12"; // limit results to twelve
			System.out.println("QRY = "+searchQry);
			ResultSet rs = st2.executeQuery(searchQry);
			
			while(rs.next()) {
				if(rs.getInt("is_visible") == 0)
					continue; // skip this post, because its  not public yet
				
				// get the post properties
				Post post = new Post(rs.getInt("post_id"));
				post.setTitle(rs.getString("title"));
				post.setUriName(rs.getString("uri_name"));
				post.setCreateDate(rs.getDate("create_date"));
				post.setPublishDate(rs.getDate("publish_date"));
				//post.setAuthorId(rs.getInt("user_id"));
				post.setAuthor("Austin Delamar");
				post.setDescription(rs.getString("description"));
				//post.setHtmlContent(rs.getString("html_content"));
				//post.setIs_visible(rs.getInt("is_visible")==1);
				//post.setIsFeatured(rs.getInt("is_featured")==1);
				post.setModifyDate(rs.getDate("modify_date"));
				post.setThumbnail(rs.getString("thumbnail"));
				//post.setBanner(rs.getString("banner"));
				//post.setBannerCaption(rs.getString("banner_caption"));
				
				// add to results list
				results.add(post);
			}
			
			// gather tags
			//System.out.println("Gathering tags...");
			for(Post p : results)
			{
				ResultSet rs2 = st2.executeQuery("select * from tags where post_id = "+p.getId());
				
				// get this post's tags - there could be more than 1
				ArrayList<String> post_tags = new ArrayList<String>();
				while(rs2.next()) {
					post_tags.add(rs2.getString("tag_name"));
				}
				p.setTags(post_tags);
			}
			
			//System.out.println(results.size()+" result(s) found.");
			
			servletRequest.setAttribute("results", results);
			
			return SUCCESS;
		
		} catch (Exception e) {
			try {
				conn2.rollback();
			} catch (SQLException e1) {}
			addActionError("An error occurred: "+e.getMessage());
			e.printStackTrace();
			return ERROR;
		} finally {
			try {
				st2.close();
				conn2.close();
			} catch (Exception e) {}
		}
	}
	
	public ArrayList<String> getTagOptions() {
		return tagOptions;
	}

	public void setTagOptions(ArrayList<String> tagOptions) {
		this.tagOptions = tagOptions;
	}

	public ArrayList<Author> getAuthorOptions() {
		return authorOptions;
	}

	public void setAuthorOptions(ArrayList<Author> authorOptions) {
		this.authorOptions = authorOptions;
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
		this.title = ApplicationStore.removeBadChars(title);
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = ApplicationStore.removeBadChars(date);
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = ApplicationStore.removeBadChars(year);
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = ApplicationStore.removeBadChars(month);
	}
	
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = ApplicationStore.removeBadChars(author);
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = ApplicationStore.removeBadChars(tag);
	}

	public ArrayList<Post> getResults() {
		return results;
	}

	public void setResults(ArrayList<Post> results) {
		this.results = results;
	}
}