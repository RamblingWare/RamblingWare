package com.rant.objects;

/**
 * Simple Database Bean object
 * 
 * @author Austin Delamar
 * @date 3/12/2017
 */
public class Database implements Comparable<Database> {

    private String host;
    private String port;
    private String url;
    private String username;
    private String password;
    private boolean adminParty = true;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdminParty() {
        return adminParty;
    }

    public void setAdminParty(boolean adminParty) {
        this.adminParty = adminParty;
    }

    @Override
    public int compareTo(Database db) {
        int comp = this.url.compareTo(db.url);
        if(comp == 0) {
            comp = this.host.compareTo(db.host);
        }
        if(comp == 0) {
            comp = this.port.compareTo(db.port);
        }
        if(comp == 0) {
            comp = this.username.compareTo(db.username);
        }
        if(comp == 0) {
            comp = this.password.compareTo(db.password);
        }
        return comp;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append("  URL: " + getUrl());
        string.append("\n  User: " + getUsername());
        string.append("\n  Password: *********");
        return string.toString();
    }

}