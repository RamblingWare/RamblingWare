package com.rant.model;

import java.util.Date;

import com.rant.config.Utils;

/**
 * This class represents an Author
 * 
 * @author Austin Delamar
 * @created 11/24/2015
 */
public class Author {

    private String _id;
    private String _rev;
    private String name;
    private String email;
    private String username;
    private String password;
    private Role role;

    private boolean isOTPEnabled;
    private boolean isOTPAuthenticated;
    private String keySecret;
    private String keyRecover;
    private String keyReset;

    private Date createDate;
    private Date modifyDate;
    private Date lastLoginDate;

    private String thumbnail;
    private String description;
    private String content;

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
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreateDateReadable() {
        return Utils.formatReadableDate(createDate);
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getModifyDateReadable() {
        return Utils.formatReadableDate(modifyDate);
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public String getLastLoginDateReadable() {
        return Utils.formatReadableDate(lastLoginDate);
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
}
