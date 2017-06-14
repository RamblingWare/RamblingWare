package com.rw.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionSupport;
import com.rw.config.Utils;

/**
 * External Search action class
 * 
 * @author Austin Delamar
 * @date 5/9/2016
 */
public class SearchAction extends ActionSupport
        implements
            ServletResponseAware,
            ServletRequestAware {

    private static final long serialVersionUID = 1L;

    // search parameters
    private String s;

    public String execute() {

        // external search was entered
        try {
            if (s == null || s.isEmpty()) {
                s = "About";
            }

            // redirect to DuckDuckGo with the search text provided
            ServletActionContext.getResponse()
                    .sendRedirect("https://duckduckgo.com/?q=site%3Aramblingware.com+" + s);

        } catch (IOException e) {
            e.printStackTrace();
            addActionError("Error: " + e.getClass().getName() + ". Please try again later.");
            return ERROR;
        }

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

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = Utils.removeNonAsciiChars(s);
    }
}