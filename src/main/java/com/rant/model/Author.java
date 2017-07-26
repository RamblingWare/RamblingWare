package com.rant.model;

import java.util.Date;

import com.rant.config.Utils;

/**
 * This class represents an Author
 * 
 * @author Austin Delamar
 * @created 11/24/2015
 */
public class Author implements Comparable<Author>{

    private String _id;
    private String _rev;
    private String name;
    private String email;
    private Role role;
    private Date createDate;
    private String thumbnail;
    private String description;
    private String content;

    public Author() {
        // Auto-generated method stub
    }
    
    public Author(String id) {
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

    public String getUri() {
        return _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return _id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreateDateReadable() {
        return Utils.formatReadableDate(createDate);
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

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    @Override
    public int compareTo(Author author) {
        return this.name.compareTo(author.name);
    }
}
