package org.amd.action;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.amd.bean.ArchiveAware;
import org.amd.bean.Author;
import org.amd.bean.Post;
import org.amd.bean.RecentViewAware;
import org.amd.bean.User;
import org.amd.bean.UserAware;
import org.amd.model.ApplicationStore;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionSupport;

/**
 * Blog Search action class
 * @author Austin Delamar
 * @date 11/25/2015
 */
public class BlogSearchAction extends ActionSupport implements ArchiveAware, RecentViewAware, UserAware, ServletResponseAware, ServletRequestAware {

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
			ResultSet rs = st.executeQuery("select uri_name, name from users;");
			authorOptions = new ArrayList<Author>();
			while(rs.next())
			{
				authorOptions.add(new Author(rs.getString("uri_name"),rs.getString("name")));
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
		String searchQry = "select p.post_id, p.title, p.uri_name, p.thumbnail, p.is_visible, p.create_date, p.modify_date, p.description from posts p ";
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
			searchQry += " p.create_date >= '"+ApplicationStore.formatMySQLDate(beginDate)+" 00:00:00' and p.create_date <= '"+ApplicationStore.formatMySQLDate(endDate)+" 00:00:00' ";
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
				int post_id = rs.getInt("post_id");
				String post_title = rs.getString("title");
				Date create_date = rs.getDate("create_date");
				String post_uri_name = rs.getString("uri_name");
				
				// save info into an object
				Post post = new Post(post_id,post_title,post_uri_name,null,create_date);
				post.setAuthor("Austin Delamar");
				post.setDescription(rs.getString("description"));
				post.setThumbnail(rs.getString("thumbnail"));
				post.setModifyDate(rs.getDate("modify_date"));
				
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
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}
	
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
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

	@Override
	public void setRecent_view(ArrayList<Post> recent_view) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setArchive_years(ArrayList<String> archive_years) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setArchive_tags(ArrayList<String> archive_tags) {
		// TODO Auto-generated method stub
		
	}
}