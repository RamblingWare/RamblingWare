package com.rw.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionSupport;
import com.rw.config.Application;
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

    // search parameter
    private String q;

    public String execute() {

        
        if(q != null && !q.isEmpty()) {
            // POST external search
            try {
                // redirect to DuckDuckGo with the search text provided
                ServletActionContext.getResponse().sendRedirect(Application.getSetting("searchProvider")
                        + "site%3A" + Application.getSetting("domain") + ' ' + q);
                return SUCCESS;
            } catch (IOException e) {
                e.printStackTrace();
                addActionError("Error: " + e.getClass().getName() + ". Please try again later.");
                return ERROR;
            }
        } 
        else {
            // GET search page
            return INPUT;
        }
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

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = Utils.removeNonAsciiChars(q);
    }
}