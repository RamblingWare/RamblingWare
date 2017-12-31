package org.oddox.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.oddox.config.Application;
import org.oddox.config.Utils;
import org.oddox.objects.Author;

import com.opensymphony.xwork2.ActionSupport;

/**
 * Author action class
 * 
 * @author Austin Delamar
 * @date 10/23/2016
 */
public class AuthorAction extends ActionSupport implements ServletResponseAware, ServletRequestAware {

    private static final long serialVersionUID = 1L;
    protected HttpServletResponse servletResponse;
    protected HttpServletRequest servletRequest;
    private Author author;
    private String uri;

    /**
     * Returns author details.
     * 
     * @return Action String
     */
    public String execute() {

        // /author/person-name
        String uriTemp = servletRequest.getRequestURI();
        if (uri == null && uriTemp.startsWith("/author/")) {
            uri = Utils.removeBadChars(uriTemp.substring(8, uriTemp.length()));
        }

        if (uri != null && uri.length() > 0) {
            // lower-case no matter what
            uri = uri.toLowerCase();

            // search in db for author
            try {
                author = Application.getDatabaseService()
                        .getAuthor(uri, false);

                if (author != null) {

                    return SUCCESS;
                } else {
                    System.err.println("Author '" + uri + "' not found. Please try again.");
                    return NONE;
                }

            } catch (Exception e) {
                addActionError("Error: " + e.getClass()
                        .getName() + ". Please try again later.");
                e.printStackTrace();
                return ERROR;
            }
        } else {
            System.err.println("Author '" + uri + "' not found. Please try again.");
            return NONE;
        }
    }

    @Override
    public void setServletResponse(HttpServletResponse servletResponse) {
        this.servletResponse = servletResponse;
    }

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
