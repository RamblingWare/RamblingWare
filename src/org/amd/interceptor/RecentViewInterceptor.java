package org.amd.interceptor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.Cookie;

import org.amd.bean.Post;
import org.amd.bean.RecentViewAware;
import org.amd.model.ApplicationStore;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
/**
 * RecentView Interceptor class
 * @author Austin Delamar
 * @date 11/30/2015
 */
public class RecentViewInterceptor implements Interceptor {

	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("unchecked")
	@Override
	public String intercept(ActionInvocation actionInvocation) throws Exception {

		Map<String, Object> sessionAttributes = actionInvocation.getInvocationContext().getSession();
		
		// check if they have the cookie
		
		Cookie ck = getCookie("recent-view");
		if(ck != null && !ck.getValue().isEmpty())
		{
			// set the recent_view attribute with Post information if they do
			ArrayList<Post> recent_view = (ArrayList<Post>) sessionAttributes.get("recent_view");
			
			System.out.println("Setting RecentView values...");
			recent_view = new ArrayList<Post>();
			
			// NEVER TRUST USER INPUT
			ck.setValue(ApplicationStore.removeNonAsciiChars(ck.getValue()));
			ck.setValue(ApplicationStore.removeBadChars(ck.getValue()));
			String condition = "";
			for(String uri : ck.getValue().split("\\|"))
			{
				if(!uri.trim().isEmpty())
					condition += "'"+uri+"',";
			}
			condition = condition.substring(0,condition.length()-1);
			
			// get the recently viewed posts
			Connection conn = null;
			try {
				conn = ApplicationStore.getConnection();
				Statement st = conn.createStatement();
				
				// search in db for recently viewed posts
				String query = "select p.post_id, p.title, p.uri_name, p.is_visible, p.create_date, LEFT(p.html_content, 90) from posts p where p.uri_name IN ("+
						condition+") AND p.is_visible <> 0 order by p.create_date desc limit 3";
				//System.out.println(query);
				ResultSet rs3 = st.executeQuery(query);
				
				while(rs3.next()) {
					if(rs3.getInt("is_visible") == 0)
						continue; // skip this post, because its  not public yet
					
					// get the post properties
					int post_id = rs3.getInt("post_id");
					String post_title = rs3.getString("title");
					Date create_date = rs3.getDate("create_date");
					String post_uri_name = rs3.getString("uri_name");
					String description = rs3.getString(6)+"...";
					
					// save info into an object
					Post post = new Post(post_id,post_title,post_uri_name,null,create_date);
					post.setAuthor("Austin Delamar");
					post.setDescription(description);
					
					// add to results list
					recent_view.add(post);
				}
				
				// set attributes
				sessionAttributes.put("recent_view", recent_view);
				
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
			
			Action action = (Action) actionInvocation.getAction();
			if (action instanceof RecentViewAware) 
			{
				((RecentViewAware) action).setRecent_view(recent_view);
			}
			
			System.out.println("RecentView set.");
		}
		else
		{
			// no cookies
			// no recently viewed posts
			sessionAttributes.put("recent_view", null);
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

	/**
	 * Return a cookie's value by its given name.
	 * @param cookieName
	 * @return Cookie
	 */
	public Cookie getCookie(String cookieName) {
		Cookie cookies[] = ServletActionContext.getRequest().getCookies();
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
		
		// cookie will last 1 year
		cookie.setMaxAge(60 * 60 * 24 * 365);
		ServletActionContext.getResponse().addCookie(cookie);
	}
}
