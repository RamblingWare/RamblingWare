package org.rw.interceptor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import org.rw.bean.ArchiveAware;
import org.rw.bean.Post;
import org.rw.model.ApplicationStore;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
/**
 * Archive Interceptor class
 * @author Austin Delamar
 * @date 11/30/2015
 */
public class ArchiveInterceptor implements Interceptor {

	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("unchecked")
	@Override
	public String intercept(ActionInvocation actionInvocation) throws Exception {

		Map<String, Object> sessionAttributes = actionInvocation.getInvocationContext().getSession();

		ArrayList<Post> archive_featured = (ArrayList<Post>) sessionAttributes.get("archive_featured");
		ArrayList<String> archive_years = (ArrayList<String>) sessionAttributes.get("archive_years");
		ArrayList<String> archive_tags = (ArrayList<String>) sessionAttributes.get("archive_tags");
		
		if (archive_years == null || archive_years.isEmpty()) 
		{
			System.out.println("Archive was not set. Setting archive values...");
			archive_featured = new ArrayList<Post>();
			archive_years = new ArrayList<String>();
			archive_tags = new ArrayList<String>();
			
			// get the archive of posts by years and tag names
			Connection conn = null;
			try {
				conn = ApplicationStore.getConnection();
				Statement st = conn.createStatement();
				// search in db for total posts by each year
				ResultSet rs = st.executeQuery("select YEAR(create_date) as year, COUNT(*) as count from posts where is_visible <> 0 group by YEAR(create_date) order by YEAR(create_date) desc");
				
				while(rs.next()) {
					// get year and  count
					int year = rs.getInt("year");
					int count = rs.getInt("count");
					archive_years.add(year+" ("+count+")");
				}
				
				// search in db for total tags by name
				ResultSet rs2 = st.executeQuery("select t.tag_name, COUNT(*) as count from tags t inner join posts p on t.post_id = p.post_id where p.is_visible <> 0 group by t.tag_name order by COUNT(*) desc, t.tag_name");
				
				while(rs2.next()) {
					// get tag name and  count
					String tag = rs2.getString("tag_name");
					int count = rs2.getInt("count");
					archive_tags.add(tag+" ("+count+")");
				}
				
				// search in db for featured posts
				ResultSet rs3 = st.executeQuery("select p.post_id, p.title, p.uri_name, p.is_visible, p.create_date, p.thumbnail from posts p where p.is_visible <> 0 and is_featured <> 0 order by p.create_date desc limit 1");
				
				while(rs3.next()) {
					if(rs3.getInt("is_visible") == 0)
						continue; // skip this post, because its  not public yet
					
					// get the post properties
					int post_id = rs3.getInt("post_id");
					String post_title = rs3.getString("title");
					Date create_date = rs3.getDate("create_date");
					String post_uri_name = rs3.getString("uri_name");
					String thumbnail = rs3.getString("thumbnail");
					
					// save info into an object
					Post post = new Post(post_id,post_title,post_uri_name,null,create_date);
					post.setAuthor("Unknown");
					post.setThumbnail(thumbnail);
					
					// add to results list
					archive_featured.add(post);
				}
				
				
				// set attributes
				sessionAttributes.put("archive_featured", archive_featured);
				sessionAttributes.put("archive_years", archive_years);
				sessionAttributes.put("archive_tags", archive_tags);
				
				//return Action.NONE;
				
			} catch (Exception e) {
				//addActionError("Error: "+e.getClass().getName()+". Please try again later.");
				e.printStackTrace();
				return Action.ERROR;
			} finally {
				try {
					conn.close();
				} catch (SQLException e) {/*Do Nothing*/}
			}
			System.out.println("Archive set.");
		} 

		Action action = (Action) actionInvocation.getAction();
		if (action instanceof ArchiveAware) 
		{
			((ArchiveAware) action).setArchive_years(archive_years);
			((ArchiveAware) action).setArchive_tags(archive_tags);
		}
		return actionInvocation.invoke();
	}
	
	@Override
	public void init() {
		System.out.println(this.getClass().getName()+" initalized.");
	}

	@Override
	public void destroy() {
		System.out.println(this.getClass().getName()+" destroyed.");
	}
}
