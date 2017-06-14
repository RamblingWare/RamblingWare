package com.rw.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.rw.config.Utils;

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
    private long rawViews;
    private long sessionViews;

    private String category;
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

    public long getRawViews() {
        return rawViews;
    }
    
    public String getRawViewsReadable() {
        return Utils.formatLong(rawViews);
    }

    public void setRawViews(long rawViews) {
        this.rawViews = rawViews;
    }

    public long getSessionViews() {
        return sessionViews;
    }
    
    public String getSessionViewsReadable() {
        return Utils.formatLong(sessionViews);
    }

    public void setSessionViews(long sessionViews) {
        this.sessionViews = sessionViews;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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
        return Utils.formatReadableDate(createDate);
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public String getModifyDateReadable() {
        return Utils.formatReadableDate(modifyDate);
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public String getPublishDateReadable() {
        return Utils.formatReadableDate(publishDate);
    }

    public int getPublishYear() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(publishDate);
        return cal.get(Calendar.YEAR);
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