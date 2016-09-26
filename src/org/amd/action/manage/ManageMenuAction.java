package org.amd.action.manage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.amd.bean.Database;
import org.amd.bean.User;
import org.amd.bean.UserAware;
import org.amd.model.ApplicationStore;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionSupport;

/**
 * Manage Menu action class
 * @author Austin Delamar
 * @date 11/9/2015
 */
public class ManageMenuAction extends ActionSupport implements UserAware, ServletResponseAware, ServletRequestAware {

	private static final long serialVersionUID = 1L;
	private User user;
	
	// statistics 
	private String start;
	private double maxMem;
	private double freeMem;
	private double totalMem;
	private String os;
	private String java;
	private ArrayList<Database> databases;
	
	public String execute() {
		
		// gather server stats
		start = ApplicationStore.getStartDateTime();
		Runtime r = Runtime.getRuntime();
		maxMem = ((double)r.maxMemory() / 1024.0) / 1024.0;
		freeMem = ((double)r.freeMemory() / 1024.0) / 1024.0;
		totalMem = ((double)r.totalMemory() / 1024.0) / 1024.0;
		os = System.getProperty("os.name") +" ("+System.getProperty("os.version")+")";
		java = System.getProperty("java.version") +" ("+System.getProperty("java.vendor")+")";
		
		// get database size
		Connection conn = null;
		Statement st = null;
		try {
			conn = ApplicationStore.getConnection();
			st = conn.createStatement();
			ResultSet rs = st.executeQuery("SELECT table_schema, sum(data_length + index_length) AS \"SIZE\""+ 
					"FROM information_schema.TABLES GROUP BY table_schema;");
			
			databases = new ArrayList<Database>();
			double totalSize = 0.0;
			while(rs.next()) {
				String name = rs.getString(1);
				double size = ((double)rs.getInt(2) /1024.0) / 1024.0;
				totalSize += size;
				databases.add(new Database(name,size));
			}
			databases.add(new Database("TOTAL",totalSize));
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
		
		
		System.out.println("User "+user.getUsername()+" opened Manage Menu.");
		
		return SUCCESS;
	}
	
		
	public String getStart() {
		return start;
	}


	public void setStart(String start) {
		this.start = start;
	}


	public double getMaxMem() {
		return maxMem;
	}


	public void setMaxMem(double maxMem) {
		this.maxMem = maxMem;
	}


	public double getFreeMem() {
		return freeMem;
	}


	public void setFreeMem(double freeMem) {
		this.freeMem = freeMem;
	}


	public double getTotalMem() {
		return totalMem;
	}


	public void setTotalMem(double totalMem) {
		this.totalMem = totalMem;
	}


	public String getOs() {
		return os;
	}


	public void setOs(String os) {
		this.os = os;
	}


	public String getJava() {
		return java;
	}


	public void setJava(String java) {
		this.java = java;
	}


	public ArrayList<Database> getDatabases() {
		return databases;
	}


	public void setDatabases(ArrayList<Database> databases) {
		this.databases = databases;
	}


	public User getUser() {
		return user;
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
		
		// cookie will last 1 year
		cookie.setMaxAge(60 * 60 * 24 * 365);
		servletResponse.addCookie(cookie);
	}

	protected HttpServletResponse servletResponse;

	@Override
	public void setUser(User user) {
		this.user = user;		
	}

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