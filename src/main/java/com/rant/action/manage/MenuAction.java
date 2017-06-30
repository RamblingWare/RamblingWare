package com.rant.action.manage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionSupport;
import com.rant.model.Author;
import com.rant.model.AuthorAware;

/**
 * Menu action class
 * 
 * @author Austin Delamar
 * @date 11/9/2015
 */
public class MenuAction extends ActionSupport
        implements
            AuthorAware,
            ServletResponseAware,
            ServletRequestAware {

    private static final long serialVersionUID = 1L;
    private Author user;

    public String execute() {

        System.out.println("User " + user.getUsername() + " opened the Menu.");

        return SUCCESS;
    }

    public Author getUser() {
        return user;
    }

    protected HttpServletResponse servletResponse;

    @Override
    public void setUser(Author user) {
        this.user = user;
    }

    @Override
    public void setServletResponse(HttpServletResponse servletResponse) {
        this.servletResponse = servletResponse;
    }

    protected HttpServletRequest servletRequest;

    @Override
    public void setServletRequest(HttpServletRequest servletRequest) {
        this.servletRequest = servletRequest;
    }
}