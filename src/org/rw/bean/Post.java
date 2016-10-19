package org.rw.bean;

import java.util.ArrayList;
import java.util.Date;

import org.rw.model.ApplicationStore;

/**
 * Post class is just a bean.
 * @author AMD
 * @created 11/24/2015
 */
public class Post {

	private int id;
	private String title;
	private String uriName;
	
	private int authorId;
	private String author;
	private String uriAuthor;
	
	private Boolean isFeatured;
	private Boolean isVisible;

	private String thumbnail;
	private String banner;
	private String bannerCaption;
	
	private Date createDate;
	private Date modifyDate;
	
	private String description;
	private String htmlContent;
	
	private ArrayList<String> tags;
	
	public Post (int id, String title, String uriName, ArrayList<String> tags, Date createDate)
	{
		this.id = id;
		this.title = title;
		this.uriName = uriName;
		this.tags = tags;
		this.createDate = createDate;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAuthorId() {
		return authorId;
	}

	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUriName() {
		return uriName;
	}
	public void setUriName(String uriName) {
		this.uriName = uriName;
	}
	public String getUriAuthor() {
		return uriAuthor;
	}

	public void setUriAuthor(String uriAuthor) {
		this.uriAuthor = uriAuthor;
	}

	public Boolean getIsFeatured() {
		return isFeatured;
	}

	public void setIsFeatured(Boolean isFeatured) {
		this.isFeatured = isFeatured;
	}

	public Boolean getIsVisible() {
		return isVisible;
	}

	public void setIs_visible(Boolean isVisible) {
		this.isVisible = isVisible;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	
	public String getBanner() {
		return banner;
	}

	public void setBanner(String banner) {
		this.banner = banner;
	}

	public String getBannerCaption() {
		return bannerCaption;
	}

	public void setBannerCaption(String bannerCaption) {
		this.bannerCaption = bannerCaption;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ArrayList<String> getTags() {
		return tags;
	}
	public void setTags(ArrayList<String> tags) {
		this.tags = tags;
	}

	public Date getCreateDate() {
		return createDate;
	}
	
	public String getCreateDateReadable() {
		return ApplicationStore.formatReadableDate(createDate);
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifyDate() {
		return modifyDate;
	}
	
	public String getModifyDateReadable() {
		return ApplicationStore.formatReadableDate(modifyDate);
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getHtmlContent() {
		return htmlContent;
	}

	public void setHtmlContent(String htmlContent) {
		this.htmlContent = htmlContent;
	}
	
}