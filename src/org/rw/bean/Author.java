package org.rw.bean;

import java.util.Date;

import org.rw.model.ApplicationStore;

/**
 * This class represents an Author
 * @author AMD
 * @created 11/24/2015
 */
public class Author {
	
	private int id;
	private String uriName;
	private String name;
	private String email;
	private boolean isAdmin;
	
	private Date createDate;
	private Date modifyDate;
	private Date lastLoginDate;
	
	private String thumbnail;
	private String description;
	private String htmlContent;
	
	public Author(int id, String uriName, String name, Date createDate) {
		this.setUriName(uriName);
		this.setName(name);
		this.setId(id);
		this.setCreateDate(createDate);
	}

	public String getUriName() {
		return uriName;
	}

	public void setUriName(String uriName) {
		this.uriName = uriName;
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

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	public String getCreateDateReadable() {
		return ApplicationStore.formatReadableDate(createDate);
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	
	public String getModifyDateReadable() {
		return ApplicationStore.formatReadableDate(modifyDate);
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}
	
	public String getLastLoginDateReadable() {
		return ApplicationStore.formatReadableDate(lastLoginDate);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getHtmlContent() {
		return htmlContent;
	}

	public void setHtmlContent(String htmlContent) {
		this.htmlContent = htmlContent;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
}
