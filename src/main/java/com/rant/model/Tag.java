package com.rant.model;

import java.util.Date;

/**
 * Tag class
 * @author Austin Delamar
 * @created 7/13/2017
 */
public class Tag {

    private String _id;
    private String _rev;
    private String uri;
    private String name;
    private String description;
    private Date createDate;
    private Date modifyDate;

    public Tag(String id) {
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
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }
}
