package com.rant.model;

/**
 * Simple Database Bean object
 * 
 * @author Austin Delamar
 * @date 3/12/2017
 */
public class Database {

    private String name;
    private String host;
    private String port;
    private String url;
    private String username;
    private String password;

    public Database() {
        // Auto-generated constructor stub
    }

    public Database(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder("Database: " + getName());
        string.append("\nURL: " + getUrl());
        string.append("\nUser: " + getUsername());
        string.append("\nPassword: ******");
        return string.toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

}