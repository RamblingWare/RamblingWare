package com.amdelamar.database;

/**
 * This class represents a CouchDB User
 * 
 * @author amdelamar
 * @date 09/03/2017
 */
public class DatabaseUser implements Comparable<DatabaseUser> {

    // CouchDB variables
    private String _id; // org.couchdb.user:admin
    private String _rev;
    private String name;
    private String password;
    private String[] roles;
    private String type = "user";

    // TODO variables
    // lastLoginDate ISO-8601 format
    // createDate ISO-8601 format
    // modifyDate ISO-8601 format

    public DatabaseUser() {
        // empty constructor
    }

    public DatabaseUser(String _id) {
        set_Id(_id);
    }

    public String get_Id() {
        return _id;
    }

    public void set_Id(String _id) {
        if (!_id.startsWith("org.couchdb.user:")) {
            this._id = "org.couchdb.user:" + _id;
        } else {
            this._id = _id;
        }
    }

    public String get_Rev() {
        return _rev;
    }

    public void set_Rev(String _rev) {
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

    @Override
    public int compareTo(DatabaseUser user) {
        return this.name.compareTo(user.name);
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append("  _id: " + get_Id());
        string.append("\n  _rev: " + get_Rev());
        string.append("\n  name: " + getName());
        string.append("\n  password: ********");
        return string.toString();
    }
}
