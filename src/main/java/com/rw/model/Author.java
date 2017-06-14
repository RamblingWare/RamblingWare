package com.rw.model;

import java.util.Date;

import com.rw.config.Utils;

/**
 * This class represents an Author
 * 
 * @author Austin Delamar
 * @created 11/24/2015
 */
public class Author {

    private int id;
    private String uriName;
    private String name;
    private String email;
    private String username;

    private String password;
    private boolean isAdmin;
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
    private String htmlContent;

    public Author(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public boolean isAdmin() {
        return isAdmin;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
