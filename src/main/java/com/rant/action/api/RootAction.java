package com.rant.action.api;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionSupport;
import com.rant.config.Application;

/**
 * Root action class
 * 
 * @author Austin Delamar
 * @date 9/24/2017
 */
public class RootAction extends ActionSupport implements ServletResponseAware, ServletRequestAware {

    private static final long serialVersionUID = 1L;

    // JSON response
    private String rant;
    private String version;
    private String error;
    private String message;
    private Map<String, String> data;

    /**
     * Returns application information.
     * 
     * @return Action String
     */
    public String execute() {

        try {
            rant = "Welcome";
            version = Application.getString("version");
            data = new HashMap<String, String>();
            data.put("url", Application.getDatabaseService().getDatabase().getUrl());
            data.put("name", Application.getString("name"));

        } catch (Exception e) {
            error = "error";
            message = e.getMessage();
        }

        // return response
        return NONE;
    }

    protected HttpServletResponse servletResponse;

    @Override
    public void setServletResponse(HttpServletResponse servletResponse) {
        this.servletResponse = servletResponse;
    }

    protected HttpServletRequest servletRequest;

    @Override
    public void setServletRequest(HttpServletRequest servletRequest) {
        this.servletRequest = servletRequest;
    }

    public String getRant() {
        return rant;
    }

    public void setRant(String rant) {
        this.rant = rant;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }

}