package com.rant.objects;

import com.rant.config.Utils;

/**
 * This class represents an Author
 * 
 * @author Austin Delamar
 * @created 11/24/2015
 */
public class Author implements Comparable<Author> {

    private String _id;
    private String _rev;
    private String name;
    private String email;
    private Role role;
    private String roleId;
    private String thumbnail;
    private String description;
    private String content;
    private String createDate;
    private String modifyDate;

    public Author(String id) {
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

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
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

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCreateDateReadable() {
        return Utils.formatReadableDate(Utils.convertStringToDate(createDate));
    }
    
    public String getCreateDateTimeReadable() {
        return Utils.formatReadableDateTime(Utils.convertStringToDate(createDate));
    }

    public String getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(String modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getModifyDateReadable() {
        return Utils.formatReadableDate(Utils.convertStringToDate(modifyDate));
    }
    
    public String getModifyDateTimeReadable() {
        return Utils.formatReadableDateTime(Utils.convertStringToDate(modifyDate));
    }

    @Override
    public int compareTo(Author author) {
        int comp = this._id.compareTo(author._id);
        if (comp == 0) {
            comp = this.name.compareTo(author.name);
        }
        return comp;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append("  _id: " + get_Id());
        string.append("\n  _rev: " + get_Rev());
        string.append("\n  name: " + getName());
        string.append("\n  email: " + getEmail());
        string.append("\n  desc: " + getDescription());
        return string.toString();
    }
}
