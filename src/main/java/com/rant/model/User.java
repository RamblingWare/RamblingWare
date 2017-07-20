package com.rant.model;

import java.util.Date;

import com.rant.config.Utils;

/**
 * This class represents a User
 * 
 * @author Austin Delamar
 * @created 07/19/2017
 */
public class User extends Author {
    
    private String password;

    private boolean isOTPEnabled;
    private boolean isOTPAuthenticated;
    private String keySecret;
    private String keyRecover;
    private String keyReset;
    
    private Date modifyDate;
    private Date lastLoginDate;
    
    public User(String id) {
        set_Id(id);
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
}
