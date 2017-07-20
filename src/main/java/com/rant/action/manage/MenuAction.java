package com.rant.action.manage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionSupport;
import com.rant.model.User;
import com.rant.model.UserAware;

/**
 * Menu action class
 * 
 * @author Austin Delamar
 * @date 11/9/2015
 */
public class MenuAction extends ActionSupport
        implements
            UserAware,
            ServletResponseAware,
            ServletRequestAware {

    private static final long serialVersionUID = 1L;
    private User user;

    public String execute() {

        System.out.println("User " + user.getUsername() + " opened the Menu.");

        return SUCCESS;
    }

    public User getUser() {
        return user;
    }

    protected HttpServletResponse servletResponse;

    @Override
    public void setUser(User user) {
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