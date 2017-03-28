package org.rw.action;

import java.util.HashSet;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.rw.bean.Author;
import org.rw.bean.Post;
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
	private Author user;
	private boolean canSeeHidden = false;
	
	// post parameters
	private Post post;
	private String uriName;
	
	public String execute() {
				
		// /blog/post/file-name-goes-here
		
		// this allows blog posts to be shown without parameter arguments (i.e. ?uri_name=foobar&test=123 )
		String  uriTemp = servletRequest.getRequestURI().toLowerCase();
		if(uriName == null && uriTemp.startsWith("/blog/post/"))
			uriName = ApplicationStore.removeBadChars(uriTemp.substring(11,uriTemp.length()));
		else if(uriName == null && uriTemp.startsWith("/manage/viewpost/"))
		{
			uriName = ApplicationStore.removeBadChars(uriTemp.substring(17,uriTemp.length()));
			if(user != null)
				canSeeHidden = true;
		}
		
		if(uriName != null && uriName.length() > 0)
		{
			// search in db for post by title
			try {
				post = ApplicationStore.getDatabaseSource().getPost(uriName, canSeeHidden);
				
				// was post found AND is it publicly visible yet?
				if(post != null)
				{					
					// set attributes
					servletRequest.setAttribute("post", post);
					servletRequest.setCharacterEncoding("UTF-8");
					
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
					
					return Action.SUCCESS;
				}
				else
				{
					System.err.println("Post '"+uriName+"' not found. Please try again.");
					return Action.NONE;
				}
			
			} catch (Exception e) {
				addActionError("Error: "+e.getClass().getName()+". Please try again later.");
				e.printStackTrace();
				return ERROR;
			}
		}
		else
		{
			System.err.println("Post '"+uriName+"' not found. Please try again.");
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

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public String getUriName() {
		return uriName;
	}

	public void setUriName(String uriName) {
		this.uriName = uriName;
	}

	@Override
	public void setUser(Author user) {
		this.user = user;
	}
}