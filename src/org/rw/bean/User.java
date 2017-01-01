package org.rw.bean;

import java.util.Date;

/**
 * User bean class
 * @author Austin Delamar
 * @date 11/9/2015
 */
public class User {

	private String userId;
	private String name;
	private String email;
	private String username;
	private String firstName;
	private String uriName;
	private String thumbnail;
	private String description;
	
	private boolean isAdmin;
	private boolean isOTPEnabled;
	private boolean isOTPAuthenticated;
	private String keySecret;
	private String keyRecover;
	private String keyReset;
	
	
	private Date lastLoginDate;
	private Date createDate;
	private Date modifyDate;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public String getUriName() {
		return uriName;
	}

	public void setUriName(String uriName) {
		this.uriName = uriName;
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

	public String getKeySecret() {
		return keySecret;
	}

	public void setKeySecret(String keySecret) {
		this.keySecret = keySecret;
	}

	public String getKeyRecover() {
		return keyRecover;
	}

	public void setKeyRecover(String keyRecover) {
		this.keyRecover = keyRecover;
	}

	public String getKeyReset() {
		return keyReset;
	}

	public void setKeyReset(String keyReset) {
		this.keyReset = keyReset;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
