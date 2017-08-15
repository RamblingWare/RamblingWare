package com.rant.model;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.rant.config.Utils;

/**
 * This class represents a Post
 * 
 * @author Austin Delamar
 * @created 11/24/2015
 */
public class Post implements Comparable<Post> {
    
    private String _id;
    private String _rev;
    private String doctype = "post";
    private String title;
    private boolean featured;
    private boolean published;
    private String category;
    private String thumbnail;
    private String banner;
    private String bannerCaption;
    private String description;
    private String content;
    private Date createDate;
    private Date modifyDate;
    private Date publishDate;
    private String author_id;
    private Author author;
    private List<String> tags;
    private View view;

    public Post(String id) {
        this._id = id;
    }

    public String get_Id() {
        return _id;
    }

    public void set_Id(String id) {
        this._id = id;
    }

    public String get_rev() {
        return _rev;
    }

    public void set_rev(String _rev) {
        this._rev = _rev;
    }
    
    public String getDoctype() {
        return doctype;
    }

    public void setDoctype(String doctype) {
        this.doctype = doctype;
    }

    public String getUri() {
        return _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isFeatured() {
        return featured;
    }

    public void setFeatured(boolean featured) {
        this.featured = featured;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public String getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(String author_id) {
        this.author_id = author_id;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    @Override
    public int compareTo(Post post) {
        return this.publishDate.compareTo(post.publishDate);
    }

}