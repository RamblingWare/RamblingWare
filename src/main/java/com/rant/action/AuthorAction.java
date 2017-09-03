package com.rant.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.rant.config.Application;
import com.rant.config.Utils;
import com.rant.model.Author;

/**
 * Author action class
 * 
 * @author Austin Delamar
 * @date 10/23/2016
 */
public class AuthorAction extends ActionSupport
        implements
            ServletResponseAware,
            ServletRequestAware {

    private static final long serialVersionUID = 1L;

    // post parameters
    private Author author;
    private String uriName;

    public String execute() {

        // /author/person-name-goes-here
        String uri = servletRequest.getRequestURI();
        if (uriName == null && uri.startsWith("/author/")) {
            uriName = Utils.removeBadChars(uri.substring(8, uri.length()));
        }

        if (uriName != null && uriName.length() > 0) {
            // search in db for author
            try {
                author = Application.getDatabaseSource().getAuthor(uriName, false);

                if (author != null) {
                    // set attributes
                    servletRequest.setAttribute("author", author);

                    return Action.SUCCESS;
                } else {
                    System.err.println("Author '" + uriName + "' not found. Please try again.");
                    return Action.NONE;
                }

            } catch (Exception e) {
                addActionError("Error: " + e.getClass().getName() + ". Please try again later.");
                e.printStackTrace();
                return ERROR;
            }
        } else {
            System.err.println("Author '" + uriName + "' not found. Please try again.");
            return Action.NONE;
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

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Author getAuthor() {
        return author;
    }
}