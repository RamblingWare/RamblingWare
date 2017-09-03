package com.rant.objects;

/**
 * This class represents a CouchDB User
 * 
 * @author Austin Delamar
 * @created 09/03/2017
 */
public class User {
    
    // CouchDB variables
    private String _id; // org.couchdb.user:admin
    private String _rev;
    private String name;
    private String password;
    private String[] roles;
    private String type = "user";
    
    // Rant variables
    // TODO
    // lastLoginDate ISO-8601 format
    // createDate ISO-8601 format
    // modifyDate ISO-8601 format
    
    public String get_id() {
        return _id;
    }
    public void set_id(String _id) {
        this._id = _id;
    }
    public String get_rev() {
        return _rev;
    }
    public void set_rev(String _rev) {
        this._rev = _rev;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String[] getRoles() {
        return roles;
    }
    public void setRoles(String[] roles) {
        this.roles = roles;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    
}
