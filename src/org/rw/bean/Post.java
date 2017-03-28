package org.rw.bean;

import java.util.ArrayList;
import java.util.Date;

import org.rw.model.ApplicationStore;

/**
 * Post class is just a bean.
 * 
 * @author Austin Delamar
 * @date 11/24/2015
 */
public class Post {

	private int id;
	private String title;
	private String uriName;
	private boolean featured;
	private boolean visible;

	private String thumbnail;
	private String banner;
	private String bannerCaption;
	private String description;
	private String htmlContent;

	private Date createDate;
	private Date modifyDate;
	private Date publishDate;

	private Author author;
	private ArrayList<String> tags;

	public Post(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public boolean isFeatured() {
		return featured;
	}

	public void setFeatured(boolean featured) {
		this.featured = featured;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
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

	public String getHtmlContent() {
		return htmlContent;
	}

	public void setHtmlContent(String htmlContent) {
		this.htmlContent = htmlContent;
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

	public Date getPublishDate() {
		return publishDate;
	}

	public String getPublishDateReadable() {
		return ApplicationStore.formatReadableDate(publishDate);
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public ArrayList<String> getTags() {
		return tags;
	}

	public void setTags(ArrayList<String> tags) {
		this.tags = tags;
	}

}