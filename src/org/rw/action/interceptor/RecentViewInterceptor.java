package org.rw.action.interceptor;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.Cookie;

import org.apache.struts2.ServletActionContext;
import org.rw.bean.Post;
import org.rw.model.ApplicationStore;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
/**
 * RecentView Interceptor class
 * @author Austin Delamar
 * @date 11/30/2015
 */
public class RecentViewInterceptor implements Interceptor {

	private static final long serialVersionUID = 1L;
	
	@Override
	public String intercept(ActionInvocation actionInvocation) throws Exception {

		Map<String, Object> sessionAttributes = actionInvocation.getInvocationContext().getSession();
		
		// check if they have the cookie
		
		Cookie ck = getCookie("recent-view");
		if(ck != null && !ck.getValue().isEmpty())
		{
			// set the recent_view attribute with Post information if they do
			@SuppressWarnings("unchecked")
			ArrayList<Post> archive_recent = (ArrayList<Post>) sessionAttributes.get("recent_view");
			
			archive_recent = new ArrayList<Post>();
			
			// NEVER TRUST USER INPUT
			ck.setValue(ApplicationStore.removeNonAsciiChars(ck.getValue()));
			ck.setValue(ApplicationStore.removeBadChars(ck.getValue()));
			
			// Build clean list
			ArrayList<String> uriList = new ArrayList<String>();
			for(String uri : ck.getValue().split("\\|"))
			{
				if(!uri.trim().isEmpty())
					uriList.add(uri);
			}
			
			// ArrayList to String[]
			String[] list = new String[uriList.size()];
			list = uriList.toArray(list);
			
			archive_recent = ApplicationStore.getDatabaseSource().getArchiveRecent(list);
			
			// set attributes
			sessionAttributes.put("recent_view", archive_recent);
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
	public void destroy() {
		// Auto-generated method stub
	}

	@Override
	public void init() {
		// Auto-generated method stub
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
