package org.rw.bean;

import java.util.Date;

/**
 * User bean class
 * @author Austin Delamar
 * @date 11/9/2015
 */
public class User {

	private String user_id;
	private String name;
	private String email;
	private String username;
	private String firstName;
	private String uri_name;
	private String thumbnail;
	
	private boolean isAdmin;
	private boolean isOTPEnabled;
	private boolean isOTPAuthenticated;
	
	private Date lastLoginDate;
	private Date createDate;
	private Date modifyDate;

	public String getUserId() {
		return user_id;
	}

	public void setUserId(String user_id) {
		this.user_id = user_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String name) {
		this.firstName = name;
	}

	public String getUri_name() {
		return uri_name;
	}

	public void setUri_name(String uri_name) {
		this.uri_name = uri_name;
	}
	
	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public boolean isOTPEnabled() {
		return isOTPEnabled;
	}

	public void setOTPEnabled(boolean isOTPEnabled) {
		this.isOTPEnabled = isOTPEnabled;
	}

	public boolean isOTPAuthenticated() {
		return isOTPAuthenticated;
	}

	public void setOTPAuthenticated(boolean isOTPAuthenticated) {
		this.isOTPAuthenticated = isOTPAuthenticated;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

}
