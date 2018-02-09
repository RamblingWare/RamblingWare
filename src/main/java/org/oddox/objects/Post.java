package org.oddox.objects;

import java.util.Calendar;
import java.util.List;

import org.oddox.config.Utils;

/**
 * This class represents a Post
 * 
 * @author Austin Delamar
 * @created 11/24/2015
 */
public class Post implements Comparable<Post> {

    private String _id;
    private String _rev;
    private String title;
    private String authorId;
    private List<String> coauthorIds;
    private List<String> editorIds;
    private String category;
    private List<String> tags;
    private boolean featured;
    private boolean published;
    private boolean deleted;
    private String createDate;
    private String modifyDate;
    private String publishDate;
    private String thumbnail;
    private String banner;
    private String bannerCaption;
    private String description;
    private String content;

    private Author author;
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

    public String get_Rev() {
        return _rev;
    }

    public void set_Rev(String _rev) {
        this._rev = _rev;
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

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
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

    public String getCreateDate() {
        return createDate;
    }

    public String getCreateDateReadable() {
        return Utils.formatReadableDate(Utils.convertStringToDate(createDate));
    }

    public String getCreateDateTimeReadable() {
        return Utils.formatReadableDateTime(Utils.convertStringToDate(createDate));
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getModifyDate() {
        return modifyDate;
    }

    public String getModifyDateReadable() {
        return Utils.formatReadableDate(Utils.convertStringToDate(modifyDate));
    }

    public String getModifyDateTimeReadable() {
        return Utils.formatReadableDateTime(Utils.convertStringToDate(modifyDate));
    }

    public void setModifyDate(String modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public String getPublishDateReadable() {
        return Utils.formatReadableDate(Utils.convertStringToDate(publishDate));
    }

    public String getPublishDateTimeReadable() {
        return Utils.formatReadableDateTime(Utils.convertStringToDate(publishDate));
    }

    public int getPublishYear() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(Utils.convertStringToDate(publishDate));
        return cal.get(Calendar.YEAR);
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public List<String> getCoauthorIds() {
        return coauthorIds;
    }

    public void setCoauthorIds(List<String> coauthorIds) {
        this.coauthorIds = coauthorIds;
    }

    public List<String> getEditorIds() {
        return editorIds;
    }

    public void setEditorIds(List<String> editorIds) {
        this.editorIds = editorIds;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    @Override
    public int compareTo(Post post) {
        int comp = this._id.compareTo(post._id);
        if (comp == 0) {
            comp = this.title.compareTo(post.title);
        }
        return comp;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append("  _id: " + get_Id());
        string.append("\n  _rev: " + get_Rev());
        string.append("\n  uri: " + getUri());
        string.append("\n  title: " + getTitle());
        string.append("\n  desc: " + getDescription());
        return string.toString();
    }
}
